package com.example.appharrypotter

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appharrypotter.api.HarryPotterService
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarPersonagemActivity : AppCompatActivity() {
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var nameTextView: TextView
    private lateinit var speciesTextView: TextView
    private lateinit var houseTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var buscarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_personagem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textInputLayout = findViewById(R.id.textInputLayout)
        nameTextView = findViewById(R.id.nameTextView)
        speciesTextView = findViewById(R.id.speciesTextView)
        houseTextView = findViewById(R.id.houseTextView)
        imageView = findViewById(R.id.imageView)
        buscarButton = findViewById(R.id.buscarButton)

        buscarButton.setOnClickListener {
            buscarPersonagem()
        }
    }

    private fun buscarPersonagem() {
        val id = textInputLayout.editText?.text.toString().trim()

        if (id.isEmpty()) {
            Toast.makeText(this, "Digite o ID do personagem.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                buscarButton.isEnabled = false

                val personagem = withContext(Dispatchers.IO) {
                    HarryPotterService.buscarPersonagemPorId(id)
                }

                if (personagem == null) {
                    nameTextView.text = "Name:"
                    speciesTextView.text = "Species:"
                    houseTextView.text = "House:"
                    imageView.setImageDrawable(null)
                    Toast.makeText(
                        this@ListarPersonagemActivity,
                        "Personagem nao encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    nameTextView.text = "Name: ${personagem.name}"
                    speciesTextView.text = "Species: ${personagem.species}"
                    houseTextView.text = "House: ${personagem.house}"

                    if (personagem.fotoURL.isNotEmpty()) {
                        Picasso.get().load(personagem.fotoURL).into(imageView)
                    } else {
                        imageView.setImageDrawable(null)
                    }
                }
            } catch (e: Exception) {
                Log.e("ListarPersonagem", "Erro ao buscar personagem", e)
                Toast.makeText(
                    this@ListarPersonagemActivity,
                    "Erro ao buscar personagem.",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                buscarButton.isEnabled = true
            }
        }
    }
}
