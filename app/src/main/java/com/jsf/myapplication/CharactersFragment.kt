package com.jsf.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jsf.myapplication.domain.adapter.CharactersAdapter
import com.jsf.myapplication.domain.model.Character
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private val viewModel: CharactersViewModel by viewModels()
    private val charactersAdapter =
        CharactersAdapter({ character -> navigateToCharacterDetail(character) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeCharacters()
        getCharacters()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun navigateToCharacterDetail(character: Character) {
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                character
            )
        )
    }

    private fun setupRecycler() {
        val recycler = view?.findViewById<RecyclerView>(R.id.charactersList)
        recycler?.adapter = charactersAdapter
    }

    // Observamos el state dentro de una corrutina.
    private fun observeCharacters() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { viewState ->
                    when (viewState) {
                        is ViewState.Success -> displayCharacters(viewState.characters)
                        is ViewState.Error -> displayError(viewState.error)
                    }
                }
            }
        }
    }

    private fun getCharacters() {
        viewModel.getCharacters()
    }

    private fun displayCharacters(characters: List<Character>) {
        charactersAdapter.submitList(characters)
    }

    private fun displayError(error: Throwable) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

}