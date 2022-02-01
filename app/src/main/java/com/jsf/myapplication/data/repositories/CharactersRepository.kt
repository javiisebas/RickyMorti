package com.jsf.myapplication.data.repositories

import com.jsf.myapplication.domain.mappers.CharacterDetailMapper
import com.jsf.myapplication.domain.mappers.CharacterMapper
import com.jsf.myapplication.domain.model.Character
import com.jsf.myapplication.domain.model.CharacterDetail
import com.jsf.myapplication.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// {} con map crea una función anónima (lambda)
class CharactersRepository {
    suspend fun getCharacters(): List<Character> =
        withContext(Dispatchers.IO){
            Api.apiService.getCharacters().results.map { character ->
                CharacterMapper.convertData(character)
            }
        }

    suspend fun getCharacterDetail(id: Int): CharacterDetail =
        withContext(Dispatchers.IO){
            CharacterDetailMapper.convertData(Api.apiService.getCharacterDetail(id))
        }

}

// Dispatchers.Default -> Trabajo que suponga un esfuerzo a la maquina (segundo plano)
// Dispatchers.Main -> Trabajo en el hilo principal
// Dispatchers.IO -> Operaciones Input-Output
// Dispatchers.Unconfined -> Testing... No es útil por ahora