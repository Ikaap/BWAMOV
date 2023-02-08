package com.ikapurwanti.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize // dibuat parcelable dengan tujuan jika model ini dibawa kemana saja enak, atau agar dapat diakses dimanapun
data class Checkout (
    var kursi: String ? = "", // dibuat nullable, jadi kalo datanya kosong nanti jadi string
    var harga: String ? = ""
) : Parcelable
