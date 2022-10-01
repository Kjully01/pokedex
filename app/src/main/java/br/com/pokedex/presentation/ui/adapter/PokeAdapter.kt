package br.com.pokedex.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.ItemRecyclerViewBinding

class PokeAdapter : RecyclerView.Adapter<PokeAdapter.ViewHolderPoke>() {

    private var imageList: MutableList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPoke {
        val itemBinding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderPoke(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderPoke, position: Int) {
        holder.onBind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(imageListAux: List<String>) {
        imageList.clear()
        imageList.addAll(imageListAux)
        notifyDataSetChanged()
    }

    class ViewHolderPoke(val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokeInfo: String) {
            binding.tvName.text = pokeInfo
        }

    }
}