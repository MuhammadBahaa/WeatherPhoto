package muhammad.bahaa.robustatask.ui.photo_preview

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import muhammad.bahaa.robustatask.data.api.ApiBuilder
import muhammad.bahaa.robustatask.data.models.WeatherResponse
import muhammad.bahaa.robustatask.data.preference.PreferenceManager
import muhammad.bahaa.robustatask.ui.base.BaseViewModel
import muhammad.bahaa.robustatask.utils.INTENT_KEY_SAVE_MODE
import muhammad.bahaa.robustatask.utils.SingleLiveEvent

class PhotoViewModel : BaseViewModel() {

    var weatherResponse = ObservableField<WeatherResponse?>()
    var isSaveModeEnable = ObservableField<Boolean>()
    private val _savePhotoEvent = SingleLiveEvent<Unit>()
    val savePhotoEvent: LiveData<Unit>
        get() = _savePhotoEvent
    private val _saveModeEvent = SingleLiveEvent<Unit>()
    val saveModeEvent: LiveData<Unit>
        get() = _saveModeEvent
    private val _sharePhotoEvent = SingleLiveEvent<Unit>()
    val sharePhotoEvent: LiveData<Unit>
        get() = _sharePhotoEvent


    fun onIntentReceived(intent: Intent){
        isSaveModeEnable.set(intent.getBooleanExtra(INTENT_KEY_SAVE_MODE,false))
        _saveModeEvent.call()
    }

    fun onSavePhotoButtonClicked() {
        _savePhotoEvent.call()
    }

    fun onSharePhotoButtonClicked() {
        _sharePhotoEvent.call()
    }

    fun getWeatherData(lat: String, lon: String) {
        makeApiRequest(ApiBuilder.weatherServices.getCurrentLocationWeatherData(lat.toDouble(), lon.toDouble()), {
            weatherResponse.set(it)
        }, {

        })
    }

    fun saveImageToStorage(uri: String){
        PreferenceManager.addPhoto(uri)
    }

}