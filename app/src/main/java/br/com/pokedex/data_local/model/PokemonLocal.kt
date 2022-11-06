package br.com.pokedex.data_local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.pokedex.model.PokemonStat
import br.com.pokedex.model.PokemonType
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "pokemon_table")
data class PokemonLocal (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
//    val types: List<PokemonType>,
//    val stats: List<PokemonStat>
): Parcelable