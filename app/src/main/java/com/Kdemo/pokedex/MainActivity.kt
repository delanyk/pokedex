package com.Kdemo.pokedex

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kdemo.pokedex.ui.PokeViewAdapter
import com.Kdemo.pokedex.ui.Pokemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    lateinit var pokeAdapter:PokeViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokeRecView = findViewById<RecyclerView>(R.id.pokeRecView)

        val data: String? = Utils().getAssetJsonData(applicationContext,"pokemon.json");

        val gson = Gson()
        val pokeListType = object : TypeToken<List<Pokemon>>() {}.type
        val pokemon: ArrayList<Pokemon> = gson.fromJson(data, pokeListType)

        pokeAdapter = PokeViewAdapter(this)
        pokeAdapter.setListPokemon(pokemon)
        pokeRecView.adapter = pokeAdapter

        pokeRecView.layoutManager = GridLayoutManager(this, 2)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokeAdapter.filter.filter(newText)
                return false
            }

        })

        return true
    }



}