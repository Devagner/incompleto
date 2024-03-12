package com.example.upeapp12.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.upeapp12.Fragments.AvisosFragment
import com.example.upeapp12.Fragments.ChatFragment
import com.example.upeapp12.Fragments.PoliFragment
import com.example.upeapp12.Fragments.VagasFragment

class Viewpageradapter(
    val abas: List<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return abas.size

    }

    override fun createFragment(position: Int): Fragment {
        when(position){



            1 -> return PoliFragment()
            2 -> return VagasFragment()
            3 -> return ChatFragment()


        }
        return AvisosFragment()
    }

}