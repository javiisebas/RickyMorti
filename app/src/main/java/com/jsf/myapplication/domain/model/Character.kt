package com.jsf.myapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character (
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val location: String,
    val image: String,
    val episode: String,
    var episodeName: String? = null
) : Parcelable

