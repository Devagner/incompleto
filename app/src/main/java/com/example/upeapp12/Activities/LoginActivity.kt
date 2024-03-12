package com.example.upeapp12.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.upeapp12.databinding.ActivityLoginBinding
import com.example.upeapp12.utius.exibirmensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    //usuário
    private lateinit var email: String
    private lateinit var senha: String

    //firebase
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializareventosclick()
        //firebaseAuth.signOut()
    }

    override fun onStart() {
        super.onStart()
        VerificarUsuarioLogado()
    }

    private fun VerificarUsuarioLogado() {
        val usuarioatual = firebaseAuth.currentUser
        if(usuarioatual != null){
            startActivity(Intent(this, MainActivity::class.java))

        }
    }

    private fun inicializareventosclick() {
        binding.textCadastro.setOnClickListener {
            startActivity(
                Intent(this, CadastroActivity::class.java)
            )
        }
        binding.btnLogar.setOnClickListener() {
            if (ValidarCampos()) {
                LogarUsuario()
            }
        }
    }

    private fun LogarUsuario() {

        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
            exibirmensagem("logado com sucesso")
            startActivity(Intent(this, MainActivity::class.java))

        }.addOnFailureListener { erro ->

            try {
                throw erro
            } catch (errousuarioinvalido: FirebaseAuthInvalidUserException) {
                errousuarioinvalido.printStackTrace()
                exibirmensagem("Email não cadastrado")

            }catch (errocredenciaisinvalidas: FirebaseAuthInvalidCredentialsException) {
                errocredenciaisinvalidas.printStackTrace()
                exibirmensagem("Email ou a  senha estão incorretos")

            }
        }
    }
    private fun ValidarCampos(): Boolean {

        email = binding.EditLoginEmail.text.toString()
        senha = binding.EditLoginSenha.text.toString()

        if (email.isNotEmpty()) {// não tá vazio
            binding.textInputLayoutLoginEmail.error = null
            if (senha.isNotEmpty()) {
                binding.textInputLayoutLoginSenha.error = null
                return true

            } else {
                binding.textInputLayoutLoginSenha.error = "Preencha a sua senha"
                return false
            }

        } else {
            binding.textInputLayoutLoginEmail.error = "Preencha o E-mail"
            return false
        }

    }

}