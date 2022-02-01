package com.jsf.myapplication.domain.model


data class CharacterDetail (
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>
)

data class Origin(
    val name: String,
    val src: String
)

data class Location(
    val name: String,
    val src: String
)