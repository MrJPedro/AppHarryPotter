package com.example.appharrypotter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appharrypotter.R
import com.example.appharrypotter.model.Estudante
import com.squareup.picasso.Picasso

class EstudanteAdapter(private var lista: List<Estudante>) :
    RecyclerView.Adapter<EstudanteAdapter.EstudanteViewHolder>() {

    inner class EstudanteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.imageView1)
        val nome: TextView = itemView.findViewById(R.id.textView1)
        val casa: TextView = itemView.findViewById(R.id.txtCasaEstudante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudanteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_estudante, parent, false)
        return EstudanteViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudanteViewHolder, position: Int) {
        val estudante = lista[position]
        holder.nome.text = estudante.name
        holder.casa.text = estudante.house

        if (estudante.fotoURL.isNotEmpty()) {
            Picasso.get()
                .load(estudante.fotoURL)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.foto)
        } else {
            holder.foto.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount() = lista.size

    fun atualizarLista(novaLista: List<Estudante>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}