package com.jsf.myapplication.domain.model

import java.lang.IllegalArgumentException


enum class CharacterStatus {
    ALIVE,
    DEAD,
    UNKNOWN;

    companion object {
        fun fromString(status: String) =
            when(status){
                "Alive" -> ALIVE
                "Dead" -> DEAD
                "unknown" -> UNKNOWN
                else -> throw IllegalArgumentException()
            }
    }
}