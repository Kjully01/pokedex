package br.com.pokedex.presentation.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.ItemRecyclerViewBinding
import br.com.pokedex.model.Pokemon
import coil.load
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class PokeAdapter(
    private val onPokemonClickListener: PokemonClickListener
) : RecyclerView.Adapter<PokeAdapter.ViewHolderPoke>() {

    private var pokeList: MutableList<Pokemon> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPoke {
        val itemBinding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderPoke(itemBinding, onPokemonClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderPoke, position: Int) {
        holder.onBind(pokeList[position])
    }

    override fun getItemCount(): Int = pokeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(pokeListAux: List<Pokemon>) {
        pokeList.clear()
        pokeList.addAll(pokeListAux)
        notifyDataSetChanged()
    }

    class ViewHolderPoke(
        private val binding: ItemRecyclerViewBinding,
        private val onPokemonClickListener: PokemonClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokeInfo: Pokemon) {

            binding.apply {
                idNumber.text = "#${pokeInfo.formattedNumber}"
                tvName.text = pokeInfo.name
                imagePoke.load(pokeInfo.imageUrl)

                tvType1.text = pokeInfo.types[0].name

                if (pokeInfo.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.text = pokeInfo.types[1].name
                } else {
                    tvType2.visibility = View.GONE
                }

                if (pokeInfo.isFavorite) {
                    icFavorite.visibility = View.VISIBLE
                    icFavoriteOut.visibility = View.INVISIBLE
                }

                root.icFavorite.setOnClickListener {
                    onPokemonClickListener.onFavoriteClickListener(pokeInfo)
                    icFavoriteOut.visibility = View.VISIBLE
                    icFavorite.visibility = View.INVISIBLE
                }

                root.icFavoriteOut.setOnClickListener {
                    onPokemonClickListener.onFavoriteClickListener(pokeInfo)
                    icFavorite.visibility = View.VISIBLE
                    icFavoriteOut.visibility = View.INVISIBLE
                }

                root.setOnClickListener {
                    onPokemonClickListener.onPokemonClickListener(pokeInfo)
                }

            }

        }

    }
}