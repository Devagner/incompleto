package com.example.upeapp12.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.upeapp12.databinding.ActivityCadastroBinding
import com.example.upeapp12.models.Usuarios
import com.example.upeapp12.utius.exibirmensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String

    //Firebase
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val firebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciartoolbar()
        inicializareventosdeclick()
    }

    private fun inicializareventosdeclick() {
        binding.BtnCadastrar.setOnClickListener{
            if(validarcampos()){
                //criar usuário se validado
                cadastrarusuario(nome,email,senha)




            }
        }
    }

    private fun cadastrarusuario(nome: String, email: String, senha: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            email,senha
        ).addOnCompleteListener { resultado ->
            if(resultado.isSuccessful){

                //salvar dados do usuário

                // id do usuario
                val idUsuario = resultado.result.user?.uid
                if(idUsuario != null){
                    val usuario = Usuarios(idUsuario, nome, email)
                    salvarusuariofirestore( usuario )
                }
            }
            //// erros para o usuario
        }.addOnFailureListener { erro ->

            try{
                throw erro
            }catch (errosenhafraca: FirebaseAuthWeakPasswordException ){
                errosenhafraca.printStackTrace()
                exibirmensagem("Senha fraca, digite com letras e números")
            }catch (erroemailemuso: FirebaseAuthUserCollisionException ){
                erroemailemuso.printStackTrace()
                exibirmensagem("E-mail já em uso")
            }catch (erroCredenciaisinvalidas: FirebaseAuthInvalidCredentialsException ){
                erroCredenciaisinvalidas.printStackTrace()
                exibirmensagem("E-mail invalido")
            }
        }
    }

    private fun salvarusuariofirestore(usuario: Usuarios) {
        firestore
            .collection("usuarios")
            .document(usuario.id)
            .set(usuario)
            .addOnSuccessListener {
                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                )
                exibirmensagem("Sucesso no cadastro")
            }.addOnFailureListener {
                exibirmensagem("Erro no cadastro")
            }

    }

    private fun validarcampos(): Boolean {

        nome = binding.EditNome.text.toString()
        email = binding.EditEmail.text.toString()
        senha = binding.EditSenha.text.toString()

        if( nome.isNotEmpty()){
            binding.TextInputLayoutNome.error = null

            if( email.isNotEmpty()){
                binding.TextInputLayoutEmail.error = null
                if( senha.isNotEmpty()){
                    binding.TextInputLayoutSenha.error = null
                    return true
                }
                else{
                    binding.TextInputLayoutSenha.error = "Preencha a sua senha!"
                    return false
                }
            }
            else{
                binding.TextInputLayoutEmail.error = "Preencha o seu email!"
                return false
            }
        }
        else{
            binding.TextInputLayoutNome.error = "Preencha o seu nome!"
            return false
        }
    }

    private fun iniciartoolbar() {
        val tooolbar = binding.IncludeToolBaar.tbprincipal
        setSupportActionBar(tooolbar)
        supportActionBar?.apply {
            title = "Faça o seu cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}