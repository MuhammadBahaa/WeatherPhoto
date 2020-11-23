package muhammad.bahaa.robustatask.ui.save_photo

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import muhammad.bahaa.robustatask.data.api.ApiBuilder
import muhammad.bahaa.robustatask.data.api.WeatherService
import muhammad.bahaa.robustatask.data.models.WeatherResponse
import muhammad.bahaa.robustatask.ui.base.BaseViewModel
import muhammad.bahaa.robustatask.utils.SingleLiveEvent

class SavePhotoViewModel : BaseViewModel() {

    private val _savePhotoEvent = SingleLiveEvent<Unit>()
    val savePhotoEvent: LiveData<Unit>
        get() = _savePhotoEvent


    var weatherResponse = ObservableField<WeatherResponse?>()


    fun onSavePhotoButtonClicked() {
        _savePhotoEvent.call()
    }

    fun getWeatherData(lat: String, lon: String) {
        makeApiRequest(ApiBuilder.weatherServices.getCurrentLocationWeatherData(lat.toDouble(), lon.toDouble()), {
            weatherResponse.set(it)
        }, {

        })
    }
}