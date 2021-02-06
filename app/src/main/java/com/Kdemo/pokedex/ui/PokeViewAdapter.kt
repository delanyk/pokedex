package com.Kdemo.pokedex.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.Kdemo.pokedex.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class PokeViewAdapter (val context: Context): RecyclerView.Adapter<PokeViewAdapter.ViewHolder>(), Filterable{

    private var pokemon: ArrayList<Pokemon>? = null
    private var pokemonFull: ArrayList<Pokemon>? = null
    private val myOptions = RequestOptions().override(150, 150);
    private val zeroPadding = "000"
    private val speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/"
    private val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poke_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemon!!.size
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility", "InflateParams")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokeName.text = pokemon!!.get(position).name
        val idx = pokemon!!.get(position).idx
        if (idx.toInt() >= 100){
            holder.indexNum.text = idx
        } else {
            holder.indexNum.text = zeroPadding.substring(idx.length) + idx
        }
        val media = pokemon!!.get(position).url

        holder.pokeCard.setOnClickListener {


            val inflater = LayoutInflater.from(context)
            val popupView: View = inflater.inflate(R.layout.poke_data_card, null)

            val pokeApi = PokeAPIParser(popupView)
            pokeApi.fetchPokeData(pokemonUrl+idx)

            val name = popupView.findViewById<TextView>(R.id.pokeDataSpecies)
            val index = popupView.findViewById<TextView>(R.id.pokeDataNum)
            val pokeImage = popupView.findViewById<ImageView>(R.id.pokeDataImage)


            name.text = pokemon!!.get(position).name
            if (idx.toInt() >= 100){
                index.text = idx
            } else {
                index.text  = zeroPadding.substring(idx.length) + idx
            }

            val myPokeCardOptions = RequestOptions().override(200, 200);
            Glide.with(context).asBitmap()
                .load(media)
                .apply(myPokeCardOptions)
//            .fitCenter()
                .into(pokeImage)


            // create the popup window
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true // lets taps outside the popup also dismiss it

            val popupWindow = PopupWindow(popupView, width, height, focusable)
            popupWindow.setTouchable(true)

            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)



        }
        Glide.with(context).asBitmap()
            .load(media)
            .apply(myOptions)
//            .fitCenter()
            .into(holder.pokeImageView)
    }

    fun setListPokemon(pokemon: ArrayList<Pokemon>){
        this.pokemon = pokemon
        this.pokemonFull = ArrayList<Pokemon>(pokemon)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var pokeName = itemView.findViewById<TextView>(R.id.pokeName)
        var indexNum = itemView.findViewById<TextView>(R.id.indexNumber)
        var pokeImageView = itemView.findViewById<ImageView>(R.id.pokeImageView)
        val pokeCard = itemView.findViewById<CardView>(R.id.pokeCard)


    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filteredList = ArrayList<Pokemon>()

                if (query.toString().isEmpty()){
                    filteredList.addAll(pokemonFull!!)
                } else{
                    val filterPattern: String = query.toString().toLowerCase(Locale.ROOT).trim()

                    for (item:Pokemon in pokemonFull!!) {
                        if (item.name.toLowerCase(Locale.ROOT).startsWith(filterPattern)){
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(query: CharSequence?, results: FilterResults?) {
                pokemon!!.clear()
                pokemon!!.addAll(results?.values as Collection<Pokemon>)
                notifyDataSetChanged()

            }

        }
    }

}
