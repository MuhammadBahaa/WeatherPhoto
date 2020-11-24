package muhammad.bahaa.robustatask.ui.base

import androidx.databinding.ObservableBoolean

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import muhammad.bahaa.robustatask.data.models.NetworkError
import retrofit2.Response

interface ApiInterface {
    val compositeDisposable: CompositeDisposable
    val isBusy: ObservableBoolean
    fun doPreApiRequest() {}
    fun doOnApiSuccess(response: Response<*>?) {}
    fun doOnApiFailure() {}
    fun onNetworkConnection() {}
    fun <R> makeApiRequest(
            networkCall: Single<Response<R>>?,
            onSuccess: (R?) -> Unit = {},
            onFailure: (NetworkError) -> Unit = {}
    )
}