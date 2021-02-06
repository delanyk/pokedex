package com.Kdemo.pokedex

import android.content.Context
import java.io.IOException
class Utils {
    fun getAssetJsonData(context: Context,file:String): String? {
        val json: String?
        try {
            val inputStream = context.assets.open(file)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            json = String(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        // print the data

        return json
    }
}

