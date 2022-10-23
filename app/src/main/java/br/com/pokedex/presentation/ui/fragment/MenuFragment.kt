package br.com.pokedex.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokedex.R
import br.com.pokedex.databinding.FragmentMenuBinding
import br.com.pokedex.model.Pokemon
import br.com.pokedex.presentation.ui.adapter.PokeAdapter
import br.com.pokedex.presentation.ui.adapter.PokemonClickListener
import br.com.pokedex.presentation.viewmodel.PokeViewModel

class MenuFragment : Fragment(), PokemonClickListener {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    private lateinit var adapterRecyclerView: PokeAdapter
    private lateinit var viewModel: PokeViewModel

    //private lateinit var viewModel: PokeViewModel by ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        observer()
        startAdapter()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemFavorite -> {
                if (item.isChecked){
                    item.setIcon(R.drawable.ic_favorite_outline)
                    item.isChecked = false
                } else {
                    item.setIcon(R.drawable.ic_favorite)
                    item.isChecked = true
                }
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun startAdapter() {
        adapterRecyclerView = PokeAdapter(this)
        binding.rvPoke.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecyclerView
        }
    }

    private fun setDataAdapter(list: List<Pokemon>) {
        adapterRecyclerView.setData(list)
    }

    override fun onPokemonClickListener(pokemon: Pokemon) {
        findNavController().navigate(
            MenuFragmentDirections.actionMenuFragmentToPokemonFragment(pokemon.id)
        )
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