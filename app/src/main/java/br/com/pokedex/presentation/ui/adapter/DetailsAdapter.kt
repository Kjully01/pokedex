package br.com.pokedex.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.ItemRecyclerViewBinding
import br.com.pokedex.databinding.ItemRecyclerViewStatsBinding
import br.com.pokedex.model.PokemonStat

class DetailsAdapter() : RecyclerView.Adapter<DetailsAdapter.ViewHolderDetails>() {

    private var statList: MutableList<PokemonStat> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetails {
        val itemBinding =
            ItemRecyclerViewStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderDetails(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderDetails, position: Int) {
        holder.onBind(statList[position])
    }

    override fun getItemCount(): Int = statList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(statListAux: List<PokemonStat>) {
        statList.clear()
        statList.addAll(statListAux)
        notifyDataSetChanged()
    }

    class ViewHolderDetails(private val binding: ItemRecyclerViewStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(stat: PokemonStat) {
            binding.apply {
                tvLabel.text = stat.nameStat
                tvInfo.text = stat.baseStat.toString()
            }
        }

    }
}