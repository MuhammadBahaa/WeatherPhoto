package muhammad.bahaa.robustatask.data.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
		@SerializedName("temp") val temp: String,
		@SerializedName("humidity") val pressure: String
)