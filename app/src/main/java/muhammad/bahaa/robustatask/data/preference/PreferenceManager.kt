package muhammad.bahaa.robustatask.data.preference

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import muhammad.bahaa.robustatask.App
import muhammad.bahaa.robustatask.utils.PREF_KEY_PHOTO_LIST
import java.lang.reflect.Type


object PreferenceManager {
        fun addPhoto(uri: String) {
            var savedPhotosList: ArrayList<String>? = getPhotosList()

            if (getPhotosList().isNullOrEmpty())
                savedPhotosList = ArrayList()
            savedPhotosList?.add(uri)
            saveArrayList(savedPhotosList)
        }

        private fun saveArrayList(list: ArrayList<String>?) {
            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(App.context)
            val editor: SharedPreferences.Editor =
                prefs.edit()
            val json: String = Gson().toJson(list)
            editor.putString(PREF_KEY_PHOTO_LIST, json)
            editor.apply()
        }

        fun getPhotosList(): ArrayList<String>? {
            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(App.context)
            val json: String? = prefs.getString(PREF_KEY_PHOTO_LIST, null)
            val type: Type = object : TypeToken<ArrayList<String>>() {}.type
            return Gson().fromJson(json, type)
        }
}