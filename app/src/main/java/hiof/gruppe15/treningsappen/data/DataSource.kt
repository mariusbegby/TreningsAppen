package hiof.gruppe15.treningsappen.data

import android.content.Context
import com.example.treningsappen.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hiof.gruppe15.treningsappen.model.Exercise
import java.io.IOException

class Datasource {
    fun loadExercises(): List<Exercise> {
        return listOf(
            Exercise(
                "Barbell Curl",

                "Barbell curl is a free weights exercise that primarily targets the biceps.",
                listOf(
                    "Stand up straight while holding a barbell in a shoulder-width grip with the palms facing forward.",
                    "Keep your elbows close to your torso and curl the barbell up while contracting the biceps.",
                    "Continue to raise the barbell until the biceps are fully contracted and the barbell is at shoulder level.",
                    "Hold the contracted position for a second and squeeze the biceps hard.",
                    "Slowly lower the barbell back to the starting position.",
                    "Repeat for the recommended amount of repetitions."
                ),
                "easy",
                "Biceps"
            ), Exercise(
                "Tricep Pushdown",

                "Tricep pushdown is a exercise machine exercise that primarily targets the triceps.",
                listOf(
                    "Attach a straight or angled bar to a high pulley and grab with an overhand grip (palms facing down) at shoulder width.",
                    "Standing upright with the torso straight and a very small inclination forward, bring the upper arms close to your body and perpendicular to the floor.",
                    "The forearms should be pointing up towards the pulley as they hold the bar. This is your starting position.",
                    "Using the triceps, bring the bar down until it touches the front of your thighs and the arms are fully extended perpendicular to the floor. The upper arms should always remain stationary next to your torso and only the forearms should move. Exhale as you perform this movement.",
                    "After a second hold at the contracted position, bring the bar slowly up to the starting point. Breathe in as you perform this step.",
                    "Repeat for the recommended amount of repetitions."
                ),
                "medium",
                "Triceps"
            ), Exercise(
                "Tricep Pushdown",
                "Tricep pushdown is a exercise machine exercise that primarily targets the triceps.",
                listOf(
                    "Attach a straight or angled bar to a high pulley and grab with an overhand grip (palms facing down) at shoulder width.",
                    "Standing upright with the torso straight and a very small inclination forward, bring the upper arms close to your body and perpendicular to the floor.",
                    "The forearms should be pointing up towards the pulley as they hold the bar. This is your starting position.",
                    "Using the triceps, bring the bar down until it touches the front of your thighs and the arms are fully extended perpendicular to the floor. The upper arms should always remain stationary next to your torso and only the forearms should move. Exhale as you perform this movement.",
                    "After a second hold at the contracted position, bring the bar slowly up to the starting point. Breathe in as you perform this step.",
                    "Repeat for the recommended amount of repetitions."
                ),
                "hard",
                "Triceps"
            )
        )
    }

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