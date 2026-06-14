package com.example.appharrypotter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appharrypotter.R
import com.example.appharrypotter.model.Feitico

class FeiticoAdapter(
    private var lista: List<Feitico>,
    private val onItemClick: (Feitico) -> Unit
) : RecyclerView.Adapter<FeiticoAdapter.FeiticoViewHolder>() {

    inner class FeiticoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.txtNomeFeitico)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeiticoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feitico, parent, false)
        return FeiticoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeiticoViewHolder, position: Int) {
        val feitico = lista[position]
        holder.nome.text = feitico.name
        holder.itemView.setOnClickListener { onItemClick(feitico) }
    }

    override fun getItemCount() = lista.size

    fun atualizarLista(novaLista: List<Feitico>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}