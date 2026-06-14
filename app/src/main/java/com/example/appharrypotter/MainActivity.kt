package com.example.appharrypotter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.listarPersonagensButton).setOnClickListener {
            startActivity(Intent(this, ListarPersonagemActivity::class.java))
        }

        findViewById<Button>(R.id.listarProfessorButton).setOnClickListener {
            startActivity(Intent(this, ListarProfessorActivity::class.java))
        }

        findViewById<Button>(R.id.listarEstudantesButton).setOnClickListener {
            startActivity(Intent(this, ListarEstudanteActivity::class.java))
        }

        findViewById<Button>(R.id.listarFeiticosButton).setOnClickListener {
            startActivity(Intent(this, ListarFeiticosActivity::class.java))
        }

        findViewById<Button>(R.id.sairButton).setOnClickListener {
            finish()
        }
    }
}
