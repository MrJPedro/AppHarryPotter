package com.example.appharrypotter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appharrypotter.adapter.FeiticoAdapter
import com.example.appharrypotter.api.HarryPotterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarFeiticosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeiticoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_feiticos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerViewFeiticos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = FeiticoAdapter(emptyList()) { feitico ->
            val intent = Intent(this, DetalhesFeiticoActivity::class.java)
            intent.putExtra("nome", feitico.name)
            intent.putExtra("descricao", feitico.description)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter

        buscarFeiticos()
    }

    private fun buscarFeiticos() {
        lifecycleScope.launch {
            try {
                val feiticos = withContext(Dispatchers.IO) {
                    HarryPotterService.listarFeiticos()
                }

                adapter.atualizarLista(feiticos)

                if (feiticos.isEmpty()) {
                    Toast.makeText(
                        this@ListarFeiticosActivity,
                        "Nenhum feitiço encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("ListarFeiticos", "Erro ao buscar feitiços", e)
                Toast.makeText(
                    this@ListarFeiticosActivity,
                    "Erro ao buscar feitiços.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}