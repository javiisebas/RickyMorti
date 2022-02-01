package com.jsf.myapplication.domain.adapter

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jsf.myapplication.R
import com.jsf.myapplication.domain.model.Character
import com.jsf.myapplication.domain.model.CharacterStatus
import com.jsf.myapplication.util.inflate
import java.lang.IllegalArgumentException


// Adapta la lista de texto a la vista del recycler
class CharactersAdapter(
    private val onClick: (Character) -> Unit,
    callback: DiffUtilCallback = DiffUtilCallback()
) :
    ListAdapter<Character, CharactersAdapter.AdapterViewHolder>(callback) {

    // view -> Estructura (vista) donde cargará los datos mapeados
    class AdapterViewHolder(view: View, val onClick: (Character) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private var currentCharacter: Character? = null

        // Cargamos los elementos del ITEM de la vista
        val name: TextView = itemView.findViewById(R.id.character_name)
        val species: TextView = itemView.findViewById(R.id.character_species)
        val image: ImageView = itemView.findViewById(R.id.character_image)
        val status: TextView = itemView.findViewById(R.id.character_status)
        val statusCircle: ImageView = itemView.findViewById(R.id.character_image_status)
        val location: TextView = itemView.findViewById(R.id.character_location)
        val episode: TextView = itemView.findViewById(R.id.character_episode)

        fun bind(character: Character) {
            currentCharacter = character

            name.text = character.name
            species.text = character.species
            status.text = when (character.status) {
                CharacterStatus.ALIVE -> "Alive"
                CharacterStatus.DEAD -> "Dead"
                CharacterStatus.UNKNOWN -> "Unknown"
            }

            when (character.status) {
                CharacterStatus.ALIVE -> statusCircle.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.status_alive,
                        null
                    )
                )
                CharacterStatus.DEAD -> statusCircle.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.status_dead,
                        null
                    )
                )
                CharacterStatus.UNKNOWN -> statusCircle.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.status_unknown,
                        null
                    )
                )
            }
            location.text = character.location
            episode.text = character.episodeName
            image.load(character.image)


            // setOnClickListener requiere una función del tipo val onClick: (Character) -> Unit
            itemView.setOnClickListener {
                onClick(character)
            }


        }
    }

    // Acción en la creación de objetos en el scroll por debajo (por defecto -> 2)
    // ViewHolder es el contenedor que representa a Item
    // ViewGroup es el contenedor que representa acitvity_main.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        // R = Resources file
        val view = parent.inflate(R.layout.item_character)
        return AdapterViewHolder(view, onClick)
    }

    // Acción en el print por pantalla de la vista creada en el onCreate
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        // holder es el AdapterViewHolder creado en el onCreate
        // holder.bind(characters[position]) // Metemos los datos en la vista para pintarlos
        holder.bind(getItem(position))
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem == newItem
    }


}
