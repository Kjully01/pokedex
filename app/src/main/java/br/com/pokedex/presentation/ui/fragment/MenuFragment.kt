package br.com.pokedex.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.data_remote.model.PokemonListResponse
import br.com.pokedex.data_remote.model.PokemonResponse
import br.com.pokedex.databinding.FragmentMenuBinding
import br.com.pokedex.model.Pokemon
import br.com.pokedex.presentation.ui.adapter.PokeAdapter
import br.com.pokedex.presentation.viewmodel.PokeViewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    private lateinit var adapterRecyclerView: PokeAdapter
    private lateinit var viewModel: PokeViewModel

    //private lateinit var viewModel: PokeViewModel by ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PokeViewModel::class.java)
        viewModel.getPokemons()

        observer()
        startAdapter()
    }

    private fun startAdapter() {
        binding.rvPoke.apply {
            adapterRecyclerView = PokeAdapter()
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecyclerView
        }
    }

    private fun setDataAdapter(list: List<Pokemon>) {
        adapterRecyclerView.setData(list)
    }

    private fun observer() {
        viewModel.apply {
            pokeSuccess.observe(viewLifecycleOwner, Observer { pokemonsResponse ->
                setDataAdapter(pokemonsResponse)
            })
            error.observe(
                viewLifecycleOwner, Observer {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}