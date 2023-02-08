package com.ikapurwanti.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize // dibuat parcelable dengan tujuan jika model ini dibawa kemana saja enak, atau agar dapat diakses dimanapun
data class Play (
    var nama: String ? = "", // dibuat nullable, jadi kalo datanya kosong nanti jadi string
    var url: String ? = ""
) : Parcelable
