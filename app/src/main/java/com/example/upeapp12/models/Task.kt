package com.example.upeapp12.models

import android.os.Parcelable
import com.example.upeapp12.utius.FireBaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id:String = "",
    var title:String = "",
    var status:Int = 0
): Parcelable{
    init {
        this.id = FireBaseHelper.Getdatabase().push().key ?: ""
    }
}
