package com.example.appharrypotter

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appharrypotter.api.HarryPotterService
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarProfessorActivity : AppCompatActivity() {
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var nameProfessorTextView: TextView
    private lateinit var alternateNamesTextView: TextView
    private lateinit var speciesProfessorTextView: TextView
    private lateinit var houseProfessorTextView: TextView
    private lateinit var buscarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_professor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textInputLayout = findViewById(R.id.textInputLayout2)
        nameProfessorTextView = findViewById(R.id.nameProfessorTextView)
        alternateNamesTextView = findViewById(R.id.nameProfessorTextView2)
        speciesProfessorTextView = findViewById(R.id.speciesProfessorTextView)
        houseProfessorTextView = findViewById(R.id.houseProfessorTextView)
        buscarButton = findViewById(R.id.button)

        buscarButton.setOnClickListener {
            buscarProfessor()
        }
    }

    private fun buscarProfessor() {
        val nome = textInputLayout.editText?.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(this, "Digite o nome do professor.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                buscarButton.isEnabled = false

                val professor = withContext(Dispatchers.IO) {
                    HarryPotterService.buscarProfessorPorNome(nome)
                }

                if (professor == null) {
                    nameProfessorTextView.text = "Name:"
                    alternateNamesTextView.text = "Alternate names:"
                    speciesProfessorTextView.text = "Species:"
                    houseProfessorTextView.text = "House:"
                    Toast.makeText(
                        this@ListarProfessorActivity,
                        "Professor nao encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val nomesAlternativos = professor.alternate_names.joinToString()

                    nameProfessorTextView.text = "Name: ${professor.name}"
                    alternateNamesTextView.text = "Alternate names: $nomesAlternativos"
                    speciesProfessorTextView.text = "Species: ${professor.species}"
                    houseProfessorTextView.text = "House: ${professor.house}"
                }
            } catch (e: Exception) {
                Log.e("ListarProfessor", "Erro ao buscar professor", e)
                Toast.makeText(
                    this@ListarProfessorActivity,
                    "Erro ao buscar professor.",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                buscarButton.isEnabled = true
            }
        }
    }
}
