package com.example.upeapp12.utius

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FireBaseHelper {

    companion object{

        fun Getdatabase() = FirebaseDatabase.getInstance().reference

        fun GetAuth() = FirebaseAuth.getInstance()

        fun GetIdUser() = GetAuth().uid

        fun isAuthentificated() = GetAuth().currentUser != null


    }
}