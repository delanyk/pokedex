package com.Kdemo.pokedex.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Kdemo.pokedex.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class PokeAPIParser(val view: View): AppCompatActivity(){
    var pokeData: PokeData? = null

    fun fetchPokeData(url:String) {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to get Request")
            }
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                pokeData = gson.fromJson(body, PokeData::class.java)
//
                println(pokeData)
            runOnUiThread{

                // views
                val pokeWeight = view.findViewById<TextView>(R.id.pokeWeight)
                val pokeHeight = view.findViewById<TextView>(R.id.pokeHeight)
                val pokeType1 = view.findViewById<TextView>(R.id.pokeType1)
                val pokeType2 = view.findViewById<TextView>(R.id.pokeType2)

                //stats
                val hp = view.findViewById<TextView>(R.id.hpNum)
                val attack = view.findViewById<TextView>(R.id.attackNum)
                val defense = view.findViewById<TextView>(R.id.defenseNum)
                val sAttack = view.findViewById<TextView>(R.id.sAttackNum)
                val sDefense = view.findViewById<TextView>(R.id.sDefenseNum)
                val speed = view.findViewById<TextView>(R.id.speedNum)


                // change text
                pokeWeight.text = (pokeData!!.weight.toFloat()/10.0).toString()+"kg"
                pokeHeight.text = (pokeData!!.height.toFloat()/10.0).toString() +"m"
                pokeType1.text = pokeData!!.types[0].type.name

                // Stat input
                hp.text = pokeData!!.stats[0].base_stat
                attack.text = pokeData!!.stats[1].base_stat
                defense.text = pokeData!!.stats[2].base_stat
                sAttack.text = pokeData!!.stats[3].base_stat
                sDefense.text = pokeData!!.stats[4].base_stat
                speed.text = pokeData!!.stats[5].base_stat

                // display proper number of types
                if (pokeData!!.types.size<2){
                    pokeType2.visibility = View.INVISIBLE

                } else{
                    pokeType2.text = pokeData!!.types[1].type.name
                }
            }

            }
        })
    }
    fun fetchSpecieData(url:String) {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to get Request")
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                pokeData = gson.fromJson(body, PokeData::class.java)
                println(pokeData)

//                runOnUiThread{
//
//
//                    val pokeWeight = view.findViewById<TextView>(R.id.pokeWeight)
//                    val pokeHeight = view.findViewById<TextView>(R.id.pokeHeight)
//                    val pokeType1  = view.findViewById<TextView>(R.id.pokeType1)
//                    pokeWeight.text = (pokeData!!.weight.toFloat()/10.0).toString()+"kg"
//                    pokeHeight.text = (pokeData!!.height.toFloat()/10.0).toString() +"m"
//                }

            }
        })
    }
}

data class PokeData(val height:String, val weight:String, val types:List<Types>, val stats:List<BaseStat>)
data class BaseStat(val base_stat: String)
data class Types(val type: BaseType)
data class BaseType(val name:String)