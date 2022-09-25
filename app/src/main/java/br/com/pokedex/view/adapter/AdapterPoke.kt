package br.com.pokedex.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.ItemRecyclerViewBinding

class AdapterPoke : RecyclerView.Adapter<AdapterPoke.ViewHolderPoke>() {

    private var imageList : MutableList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPoke {
        val itemBinding = ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderPoke(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderPoke, position: Int) {
        holder.onBind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(imageListAux : List<String>){
        imageList.clear()
        imageList.addAll(imageListAux)
        notifyDataSetChanged()
    }

    class ViewHolderPoke(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokeInfo: String) {
            binding.name.text = pokeInfo
        }

    }
}