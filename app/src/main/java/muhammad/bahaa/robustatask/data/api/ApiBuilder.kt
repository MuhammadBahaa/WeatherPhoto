package muhammad.bahaa.robustatask.data.api

import com.google.gson.GsonBuilder
import muhammad.bahaa.robustatask.utils.HTTP_REQUEST_TIMEOUT
import muhammad.bahaa.robustatask.utils.WEATHER_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBuilder {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(HTTP_REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(HTTP_REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
    }

    private val apiRetrofitInstant: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(WEATHER_API_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                GsonBuilder().serializeNulls().create()
                        )
                )
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    val weatherServices: WeatherService by lazy {
        apiRetrofitInstant.create(WeatherService::class.java)
    }
}