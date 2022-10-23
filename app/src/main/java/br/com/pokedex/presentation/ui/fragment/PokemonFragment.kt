package br.com.pokedex.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.pokedex.R
import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.databinding.FragmentPokemonBinding
import br.com.pokedex.model.Pokemon
import br.com.pokedex.presentation.viewmodel.PokeViewModel
import coil.load

class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    private val binding: FragmentPokemonBinding get() = _binding!!

    private val args: PokemonFragmentArgs by navArgs()

    private lateinit var viewModel: PokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PokeViewModel::class.java)
        viewModel.getPokemon(args.id)

        observer()
    }

    private fun setupView(pokemon: Pokemon) {
        binding.apply {
            imgPokemon.load(pokemon.imageUrl)
            tvName.text = pokemon.name

            tvType1.text = pokemon.types[0].name

            if (pokemon.types.size > 1) {
                tvType2.visibility = View.VISIBLE
                tvType2.text = pokemon.types[1].name
            } else {
                tvType2.visibility = View.GONE
            }
        }
    }

    private fun observer() {
        viewModel.apply {
            pokemonSuccess.observe(viewLifecycleOwner, Observer {
                setupView(it)
            })
        }
    }
}