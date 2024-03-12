package com.example.upeapp12.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upeapp12.Activities.MensagensActivity
import com.example.upeapp12.adapters.Chatadapter
import com.example.upeapp12.databinding.FragmentChatBinding
import com.example.upeapp12.models.Usuarios
import com.example.upeapp12.utius.Constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration



class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var eventosnapshot: ListenerRegistration
    private lateinit var chatadapter: Chatadapter
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
   private val firestore by lazy{
       FirebaseFirestore.getInstance()
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatBinding.inflate(
            inflater, container, false

        )
        chatadapter = Chatadapter{usuario ->
            val intent = Intent(context, MensagensActivity::class.java)
            intent.putExtra("dadosdestinatario", usuario)
            intent.putExtra("origem", Constantes.Origem_Contato)
            startActivity(intent)
        }
        binding.RvChat.adapter = chatadapter
        binding.RvChat.layoutManager = LinearLayoutManager(context)
        binding.RvChat.addItemDecoration(
            DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
            )
        )
        return binding.root
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onStart() {
        super.onStart()
        Adicionarouvintedecontatos()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::eventosnapshot.isInitialized) {
            eventosnapshot.remove()
        }

    }

    private fun Adicionarouvintedecontatos(){
         eventosnapshot = firestore
            .collection("usuarios")
            .addSnapshotListener { querySnapShot, erro ->
                val listacontatos = mutableListOf<Usuarios>()
                val documentos = querySnapShot?.documents
                documentos?.forEach{ DocumentSnapshot ->

                    val usuario = DocumentSnapshot.toObject( Usuarios::class.java)
                    if(usuario != null){
                        val idUsuarios = firebaseAuth.currentUser?.uid
                        if( idUsuarios != null && idUsuarios != usuario.id ){
                            Log.i("fragment_chat", "nome: ${usuario.nome}")
                            listacontatos.add( usuario)
                        }
                    }

                }
                if(listacontatos.isNotEmpty()){
                    chatadapter.adicionalista(listacontatos)
                }

            }
    }



}