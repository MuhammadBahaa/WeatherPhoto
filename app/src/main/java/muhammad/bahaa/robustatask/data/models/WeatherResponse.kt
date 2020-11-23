package muhammad.bahaa.robustatask.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
	@SerializedName("main") val data : WeatherData,
	@SerializedName("name") val cityName : String,
	@SerializedName("cod") val statusCode : Int
)