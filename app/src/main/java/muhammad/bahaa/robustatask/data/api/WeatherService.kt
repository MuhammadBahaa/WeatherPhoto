package muhammad.bahaa.robustatask.data.api

import io.reactivex.Single
import muhammad.bahaa.robustatask.data.models.WeatherResponse
import muhammad.bahaa.robustatask.utils.WEATHER_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    fun getCurrentLocationWeatherData(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") apiKey: String = WEATHER_API_KEY

    ): Single<Response<WeatherResponse>>
}