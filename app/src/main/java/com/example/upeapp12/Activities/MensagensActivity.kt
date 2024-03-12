package com.example.upeapp12.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.upeapp12.Fragments.ChatFragment
import com.example.upeapp12.R
import com.example.upeapp12.databinding.ActivityMensagensBinding
import com.example.upeapp12.models.Usuarios
import com.example.upeapp12.utius.Constantes
import com.squareup.picasso.Picasso

class MensagensActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMensagensBinding.inflate(layoutInflater)
    }
    private var dadosdestinatario: Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recuperardadosusuariosdestinatario()
        iniciartoolbar()
        iniciareventosdeclick()
    }

    private fun iniciareventosdeclick() {

        binding.FAB2.setOnClickListener{
            val mensagem = binding.Editmensagens.text.toString()
            salvarmensagem(mensagem)
        }
    }

    private fun salvarmensagem(mensagem: String) {



    }

    private fun recuperardadosusuariosdestinatario() {

        val extras = intent.extras
        if(extras != null){

            val origem = extras.getString("origem")
            if(origem == Constantes.Origem_Contato){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    dadosdestinatario = extras.getParcelable("dadosdestinatario", Usuarios::class.java)
                }else{
                    dadosdestinatario = extras.getParcelable("dadosdestinatario")
                }

            }else if( origem == Constantes.Origem_Conversa){


            }
        }
    }
    private fun iniciartoolbar() {
        val toolbar = binding.TbMensagens
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
            if(dadosdestinatario != null){
                binding.textNomePerfil.text = dadosdestinatario!!.nome
                Picasso.get()
                    .load(dadosdestinatario!!.foto)
                    .into(binding.ImagePhotoPerfil)
            }
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = ChatFragment()
            fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment)
            fragmentTransaction.commit()
        }
    }

}