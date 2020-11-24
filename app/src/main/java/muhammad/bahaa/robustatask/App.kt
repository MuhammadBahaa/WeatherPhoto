package muhammad.bahaa.robustatask

import android.content.Context
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {
    companion object {
        private lateinit var instance: App

        val context: Context
            get() = instance
    }

    @Synchronized
    fun assignInstance() {
        instance = this
    }

    init {
        assignInstance()
    }
}