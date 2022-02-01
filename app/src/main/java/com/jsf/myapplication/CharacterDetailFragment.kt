package com.jsf.myapplication

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.helper.widget.Flow
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.jsf.myapplication.domain.model.Character
import com.jsf.myapplication.domain.model.CharacterDetail
import com.jsf.myapplication.domain.model.CharacterStatus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CharacterDetailFragment() : Fragment(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeCharacters()
        getCharacterDetail(args.character.id)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun observeCharacters() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateCharacterDetail.collect { viewState ->
                    when (viewState) {
                        is ViewStateCharacterDetail.Success -> displayCharacterDetail(viewState.character)
                        is ViewStateCharacterDetail.Error -> displayError(viewState.error)
                    }
                }
            }
        }
    }

    private fun getCharacterDetail(id: Int) {
        viewModel.getCharacterDetail(id)
    }

    private fun displayCharacterDetail(character: CharacterDetail) {

        view?.let { view ->
            val name: TextView = view.findViewById(R.id.character_name_detail)
            val species: TextView = view.findViewById(R.id.character_specie_detail)
            val image: ImageView = view.findViewById(R.id.character_image_detail)
            val status: TextView = view.findViewById(R.id.character_status_detail)
            val statusFlow: Flow = view.findViewById(R.id.flow_status)
            val episodes: TextView = view.findViewById(R.id.character_episodes_detail)
            val origin: TextView = view.findViewById(R.id.character_origin_detail)
            val type: TextView = view.findViewById(R.id.character_type_detail)
            val gender: TextView = view.findViewById(R.id.character_gender_detail)

            name.text = character.name
            species.text = character.species
            status.text = when (character.status) {
                CharacterStatus.ALIVE -> "Alive"
                CharacterStatus.DEAD -> "Dead"
                CharacterStatus.UNKNOWN -> "Unknown"
            }

            val colorStatus = when (character.status) {
                CharacterStatus.ALIVE -> R.color.alive
                CharacterStatus.DEAD -> R.color.dead
                CharacterStatus.UNKNOWN -> R.color.unknown
            }
            context?.let { context ->
                val color = ResourcesCompat.getColor(resources, colorStatus, null)
                statusFlow.background.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        color,
                        BlendModeCompat.SRC_ATOP
                    )
            }

            episodes.text = character.episode.count().toString()
            origin.text = character.origin.name
            type.text = if (character.type.isEmpty()) "unknown" else character.type
            gender.text = character.gender
            image.load(character.image)
        }

    }


    private fun displayError(error: Throwable) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

}