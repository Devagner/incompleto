package com.example.upeapp12.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.upeapp12.databinding.FragmentChatBinding
import com.example.upeapp12.databinding.ItemChatBinding
import com.example.upeapp12.models.Usuarios
import com.squareup.picasso.Picasso

class Chatadapter(
    private val onClick: (Usuarios) -> Unit
): Adapter<Chatadapter.Chatviewholder>() {


    private var listachat = emptyList<Usuarios>()
    fun adicionalista(lista: List<Usuarios>){
        listachat = lista
        notifyDataSetChanged()
    }

    inner class Chatviewholder(
        private val binding: ItemChatBinding
    ): ViewHolder(binding.root){

        fun bind(usuarios: Usuarios){

            binding.textchat.text = usuarios.nome
            Picasso.get()
                .load(usuarios.foto)
                .into(binding.ImagemChat)

            //evento de click
            binding.ClItemContato.setOnClickListener{
                onClick(usuarios)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chatviewholder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemChatBinding.inflate(
            inflater, parent, false
        )
        return Chatviewholder(itemView)
    }

    override fun getItemCount(): Int {
        return listachat.size
    }

    override fun onBindViewHolder(holder: Chatviewholder, position: Int) {
       val usuario = listachat[position]
        holder.bind(usuario)
    }
}