package com.jsf.myapplication.data.repositories

import com.jsf.myapplication.domain.mappers.EpisodeMapper
import com.jsf.myapplication.domain.model.Episode
import com.jsf.myapplication.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepository {
    suspend fun getEpisodeName(url: String): Episode =
        withContext(Dispatchers.IO){
            EpisodeMapper.convertData(Api.apiService.getEpisodeName(url))
        }
}