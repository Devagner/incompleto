package com.example.upeapp12.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import com.example.upeapp12.R
import com.example.upeapp12.adapters.Viewpageradapter
import com.example.upeapp12.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val firebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciartoolbar()
        iniciarnavegacaoabas()


    }

    private fun iniciarnavegacaoabas() {

        val tabLayout = binding.tabLayoutprincipal
        val viewPager = binding.Viewpagprincipal

        //adapter
       // val abas = listOf("Noticias","Poli","Oportunidade","Chat") //ajeitar dps
        val abas = listOf("Noticias","Poli","Oportunidade","Chat")
        viewPager.adapter = Viewpageradapter(abas, supportFragmentManager, lifecycle)
        tabLayout.isTabIndicatorFullWidth = true
        TabLayoutMediator(tabLayout, viewPager){ aba, posicao ->
            aba.text = abas[posicao]
        }.attach()
    }

    private fun iniciartoolbar() {
        val toolbar = binding.IncludeMainToolbar.tbprincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Upe-app"

        }

        addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_principal, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.item_perfil ->{
                        startActivity(Intent(applicationContext, PerfilActivity::class.java))

                    }
                    R.id.item_sair ->{
                        deslogarusuario()

                    }
                   /* R.id.Conversasbtn ->{
                        startActivity(Intent(applicationContext, ContatosActivity::class.java))
                    }

                    */
                }

                return true
            }

        })
    }

    private fun deslogarusuario() {
        AlertDialog.Builder(this)
            .setTitle("Deslogar")
            .setMessage("Deseja realmente sair?")
            .setNegativeButton("Não"){dialog, posicao ->}
            .setPositiveButton("Sim"){dialog, posicao ->
             firebaseAuth.signOut()
              startActivity(Intent(applicationContext, LoginActivity::class.java)) }
            .create()
            .show()

    }
}