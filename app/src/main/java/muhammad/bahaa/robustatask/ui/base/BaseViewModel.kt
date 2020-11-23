package muhammad.bahaa.robustatask.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import muhammad.bahaa.robustatask.data.models.NetworkError
import muhammad.bahaa.robustatask.utils.APP_NETWORK_ERROR
import muhammad.bahaa.robustatask.utils.NO_INTERNET_CONNECTION_MESSAGE

import muhammad.bahaa.robustatask.utils.SingleLiveEvent
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseViewModel : ViewModel(), ApiInterface {

    private val DEFAULT_NETWORK_ERROR = NetworkError()
    private val NO_INTERNET_NETWORK_ERROR = NetworkError(message = NO_INTERNET_CONNECTION_MESSAGE, code = APP_NETWORK_ERROR)

    override val isBusy = ObservableBoolean()
    private val _toastMessage = SingleLiveEvent<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected val _terminate = SingleLiveEvent<Unit>()
    val terminate: LiveData<Unit>
        get() = _terminate

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun doPreApiRequest() {
        isBusy.set(true)
    }

    override fun doOnApiSuccess(response: Response<*>?) {
        isBusy.set(false)
    }

    override fun doOnApiFailure() {
        isBusy.set(false)
    }

    fun onClickBack() = _terminate.call()

    override fun <R> makeApiRequest(
        networkCall: Single<Response<R>>?,
        onSuccess: (R?) -> Unit,
        onFailure: (NetworkError) -> Unit
    ) {
        makeNetworkRequest(networkCall, onSuccess, onFailure)
    }

    private fun <R> makeNetworkRequest(
        networkCall: Single<Response<R>>?,
        onSuccess: (R?) -> Unit,
        onFailure: (NetworkError) -> Unit
    ) {
        doPreApiRequest()

        var disposable: Disposable? = null
        if (networkCall != null) {
            disposable = networkCall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    disposable?.let {
                        it.dispose()
                    }
                }
                .subscribe { response: Response<R>?, throwable: Throwable? ->
                    throwable?.let {
                        doOnApiFailure()
                        onFailure(NO_INTERNET_NETWORK_ERROR)
                        return@subscribe
                    } ?: doOnApiSuccess(response)

                    response?.takeIf { it.isSuccessful }?.let {
                        onSuccess(it.body())
                    }
                    ?: onFailure(DEFAULT_NETWORK_ERROR)
                }
        }
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
}