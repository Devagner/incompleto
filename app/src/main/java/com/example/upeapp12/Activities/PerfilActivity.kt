package com.example.upeapp12.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.upeapp12.databinding.ActivityPerfilBinding
import com.example.upeapp12.utius.exibirmensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }
    private val firebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    private val storage by lazy{
        FirebaseStorage.getInstance()
    }
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var tempermissaoCamera = false
    private var tempermissaoGaleria = false

    private val gerenciadordegaleria = registerForActivityResult(ActivityResultContracts.GetContent()){uRI ->
        if(uRI != null){

            binding.imagePerfil.setImageURI( uRI )
            uploidimagemstore( uRI )
        }
        else{
            exibirmensagem("Nenhuma imagem selecionada")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciartoolbar()
        solicitarpermissoes()
        inicializareventosdeclick()
    }

    override fun onStart() {
        super.onStart()
        recuperdadosiniciaisdousuario()
    }

    private fun recuperdadosiniciaisdousuario() {
        val idusuario = firebaseAuth.currentUser?.uid
        if( idusuario != null){
            firestore
                .collection("usuarios")
                .document(idusuario)
                .get()
                .addOnSuccessListener {documentSnapchot ->

                    val dadosusuario = documentSnapchot.data
                    if(dadosusuario != null){
                        val nome = dadosusuario["nome"] as String
                        val foto = dadosusuario["foto"] as String

                        binding.EditTextPerfil.setText( nome )
                        if(foto.isNotEmpty()){
                            Picasso.get()
                                .load(foto)
                                .into( binding.imagePerfil)


                        }

                    }

                }
        }
    }

    private fun inicializareventosdeclick() {
        binding.floatingActionButton.setOnClickListener(){
            if( tempermissaoGaleria){
                gerenciadordegaleria.launch("image/*")

            }else{

                exibirmensagem("Forneça permissão para a galeria")
                solicitarpermissoes()
            }
        }
        binding.btnAtualizarPerfil.setOnClickListener(){
            val nomeUsuario = binding.EditTextPerfil.text.toString()

            if( nomeUsuario.isNotEmpty()){
                val idusuario = firebaseAuth.currentUser?.uid
                if(idusuario !=null){
                    val dados = mapOf(
                        "nome" to nomeUsuario
                    )
                    atualizardadosperfil( idusuario, dados )
                }



            }else{

            }
        }
    }

    private fun uploidimagemstore(uRI: Uri) {

        //fotos -> usuarios -> iddousuario -> salvar
        val idusuario =firebaseAuth.currentUser?.uid
        if( idusuario != null) {
            storage
                .getReference("Fotos")
                .child("Usuarios")
                .child(idusuario)
                .child("Perfil.jpg" )
                .putFile(uRI)
                .addOnSuccessListener {task ->
                    exibirmensagem("Imagem atualizada com sucesso")
                    task.metadata
                        ?.reference
                        ?.downloadUrl
                        ?.addOnSuccessListener {uRI ->

                            val dados = mapOf(
                                "foto" to uRI.toString()
                            )
                            atualizardadosperfil( idusuario, dados )

                        }
                }.addOnFailureListener{
                    exibirmensagem("Erro, escolha outra imagem")
                }
        }
    }

    private fun atualizardadosperfil(idusuario: String, dados: Map<String, String>){

        firestore
            .collection("usuarios")
            .document(idusuario)
            .update(dados)
            .addOnSuccessListener {
                exibirmensagem("Sucesso ao atualizar perfil")
            }.addOnFailureListener {
                exibirmensagem("Erro ao atualizar perfil")
            }
    }


    private fun solicitarpermissoes() {

        tempermissaoCamera = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        tempermissaoGaleria = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        // LISTA DE PERMISSÕES NEGADAS
        val ListaPermissoesNegadas = mutableListOf<String>()
        if(!tempermissaoCamera){
            ListaPermissoesNegadas.add( Manifest.permission.CAMERA)
        }
        if(!tempermissaoGaleria){
            ListaPermissoesNegadas.add( Manifest.permission.READ_MEDIA_IMAGES)
        }
        if( ListaPermissoesNegadas.isNotEmpty()) {

            // solicitando muitas permissões
            val gerenciadordepermissoes = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permisoes ->
                tempermissaoCamera = permisoes[Manifest.permission.CAMERA]
                    ?: tempermissaoCamera

                tempermissaoGaleria = permisoes[Manifest.permission.READ_MEDIA_IMAGES]
                    ?: tempermissaoGaleria
            }
            gerenciadordepermissoes.launch(ListaPermissoesNegadas.toTypedArray())
        }

    }

    private fun iniciartoolbar() {
        val toolbar = binding.IncludeToolbarPerfil.tbprincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Editar perfil"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}