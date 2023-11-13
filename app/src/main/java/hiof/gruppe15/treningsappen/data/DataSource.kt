package hiof.gruppe15.treningsappen.data

import android.content.Context
import com.example.treningsappen.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hiof.gruppe15.treningsappen.model.Exercise
import java.io.IOException

class Datasource {
    fun loadExercisesFromJson(context: Context): List<Exercise> {
        val json: String
        return try {
            val inputStream = context.resources.openRawResource(R.raw.exercises)
            json = inputStream.bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Exercise>>() {}.type
            Gson().fromJson(json, listType)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            emptyList()
        }
    }
}