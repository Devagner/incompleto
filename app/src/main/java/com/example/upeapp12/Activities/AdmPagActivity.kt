package com.example.upeapp12.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.upeapp12.R
import com.example.upeapp12.databinding.ActivityAdmPagBinding
import com.example.upeapp12.databinding.ActivityMainBinding

class AdmPagActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdmPagBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbaradm()
    }

    private fun toolbaradm() {
        val toolbar = binding.tbadm.tbprincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Adm consoler"
            setDisplayHomeAsUpEnabled(true)
        }

    }
}