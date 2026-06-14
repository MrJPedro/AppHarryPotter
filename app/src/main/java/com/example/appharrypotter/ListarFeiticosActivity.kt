package com.example.appharrypotter

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appharrypotter.adapter.EstudanteAdapter
import com.example.appharrypotter.api.HarryPotterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarEstudanteActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var buscarButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EstudanteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_estudante)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup = findViewById(R.id.radioGroup)
        buscarButton = findViewById(R.id.buscarEstudantesButton)
        recyclerView = findViewById(R.id.recyclerViewEstudantes)

        adapter = EstudanteAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buscarButton.setOnClickListener {
            buscarEstudantes()
        }
    }

    private fun buscarEstudantes() {
        val casa = when (radioGroup.checkedRadioButtonId) {
            R.id.gryffindorRadioButton -> "Gryffindor"
            R.id.hufflepuffRadioButton -> "Hufflepuff"
            R.id.RavenclawRadioButton  -> "Ravenclaw"
            R.id.SlytherinRadioButton  -> "Slytherin"
            else -> {
                Toast.makeText(this, "Selecione uma casa.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        lifecycleScope.launch {
            try {
                buscarButton.isEnabled = false

                val estudantes = withContext(Dispatchers.IO) {
                    HarryPotterService.listarEstudantesDaCasa(casa)
                }

                adapter.atualizarLista(estudantes)

                if (estudantes.isEmpty()) {
                    Toast.makeText(
                        this@ListarEstudanteActivity,
                        "Nenhum estudante encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("ListarEstudante", "Erro ao buscar estudantes", e)
                Toast.makeText(
                    this@ListarEstudanteActivity,
                    "Erro ao buscar estudantes.",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                buscarButton.isEnabled = true
            }
        }
    }
}