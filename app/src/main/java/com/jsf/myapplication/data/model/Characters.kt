package com.jsf.myapplication.data.model

import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("results")
    val results: List<Character>,

    @SerializedName("info")
    val info: Info
)

