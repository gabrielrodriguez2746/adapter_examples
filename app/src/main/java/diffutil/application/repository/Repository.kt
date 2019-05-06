package diffutil.application.repository

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import diffutil.application.DiffUtilApplication
import diffutil.application.models.TvSerie

object Repository {

    private val favoritesMap : HashMap<String, Boolean> = hashMapOf()

    private val items by lazy {
        Gson().fromJson<List<TvSerie>>(
            DiffUtilApplication.instance.assets.readAssetsFile<JsonArray>("tv_items.json"),
            object : TypeToken<List<TvSerie>>() {}.type
        )
    }

    fun updateFavoriteState(id: String) {
        if (favoritesMap.containsKey(id)) {
            favoritesMap[id] = !favoritesMap.getValue(id)
        } else {
            favoritesMap[id] = !items.first { it.id == id }.isFavorite
        }
    }

    fun getItemInstanceList(): List<TvSerie> = items.map {
        if (favoritesMap.containsKey(it.id)) {
            it.copy(isFavorite = favoritesMap.getValue(it.id))
        } else {
            it
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : JsonElement> AssetManager.readAssetsFile(fileName: String): T {
        val jsonFile = open(fileName)
            .bufferedReader()
            .use { it.readText() }.trim()
        val parser = JsonParser()
        val jsonElement = parser.parse(jsonFile)
        return jsonElement as T
    }

}