package com.jsf.myapplication.domain.mappers

import com.jsf.myapplication.data.model.Character
import com.jsf.myapplication.domain.model.CharacterDetail
import com.jsf.myapplication.domain.model.Location
import com.jsf.myapplication.domain.model.Origin
import com.jsf.myapplication.domain.model.CharacterStatus

class CharacterDetailMapper {
    companion object {
        fun convertData(character: Character) = CharacterDetail(
            character.id,
            character.name,
            CharacterStatus.fromString(character.status),
            character.species,
            character.type,
            character.gender,
            Origin(character.origin.name, character.origin.url),
            Location(character.location.name, character.location.url),
            character.image,
            character.episodes
        )
    }
}