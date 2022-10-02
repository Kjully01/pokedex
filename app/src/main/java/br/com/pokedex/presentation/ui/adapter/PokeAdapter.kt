package br.com.pokedex.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.data_remote.model.PokemonResponse
import br.com.pokedex.databinding.ItemRecyclerViewBinding

class PokeAdapter : RecyclerView.Adapter<PokeAdapter.ViewHolderPoke>() {

    private var pokeList: MutableList<PokemonResponse> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPoke {
        val itemBinding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderPoke(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderPoke, position: Int) {
        holder.onBind(pokeList[position])
    }

    override fun getItemCount(): Int = pokeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(pokeListAux: List<PokemonResponse>) {
        pokeList.clear()
        pokeList.addAll(pokeListAux)
        notifyDataSetChanged()
    }

    class ViewHolderPoke(val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokeInfo: PokemonResponse) {
            binding.tvName.text = pokeInfo.name
        }

    }
}