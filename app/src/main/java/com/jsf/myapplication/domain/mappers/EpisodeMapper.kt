package com.jsf.myapplication.domain.mappers

import com.jsf.myapplication.data.model.Episode as EpisodeDataModel
import com.jsf.myapplication.domain.model.Episode

class EpisodeMapper {
    companion object {
        fun convertData(episode: EpisodeDataModel) = Episode(
            episode.id,
            episode.name
        )
    }
}
