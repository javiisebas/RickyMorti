package com.jsf.myapplication.domain.mappers

import com.jsf.myapplication.domain.model.*
import com.jsf.myapplication.data.model.Character as CharacterDataModel

class CharacterMapper {
    companion object {
        fun convertData(character: CharacterDataModel) = Character(
            character.id,
            character.name,
            CharacterStatus.fromString(character.status),
            character.species,
            character.location.name,
            character.image,
            character.episodes[0]
        )
    }
}
