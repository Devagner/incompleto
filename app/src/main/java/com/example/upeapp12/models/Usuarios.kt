package com.example.upeapp12.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuarios(
    var id: String = "",
    var nome: String = "",
    var email: String = "",
    var foto: String = ""
): Parcelable
