package muhammad.bahaa.robustatask.ui.base

import android.content.IntentFilter
import android.os.Build

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cantrowitz.rxbroadcast.RxBroadcast.fromLocalBroadcast
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.utils.ACTION_CHANGE_NETWORK_STATE
import muhammad.bahaa.robustatask.utils.EXTRA_NETWORK_IS_CONNECTED

abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {
    abstract val rootView: Int
    abstract val vMType: Class<VM>
    val viewModel: VM by lazy { ViewModelProviders.of(this).get(vMType) }
    val viewDataBinding: B by lazy {
        DataBindingUtil.setContentView<B>(this, rootView)
            .apply { lifecycleOwner = this@BaseActivity }
    }
    open val viewNetFailureSnackBarWillAttachTo: View? = null
    val compositeDisposable = CompositeDisposable()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachViewModelToView()
        listenForToasts()
        listenForNetworkChanges()
        listenForTermination()
    }

    private fun listenForTermination() {
        viewModel.terminate.observe(this, Observer { terminate() })
    }

    open fun terminate() {
        ActivityCompat.finishAfterTransition(this)
    }

    private fun listenForToasts() {
        viewModel.toastMessage.observe(this, Observer {
            it?.takeIf { it.isNullOrBlank().not() }
                ?.let { showMessageAsToast(it) }
        })
    }

    fun showMessageAsToast(@StringRes resource: Int) {
        showMessageAsToast(resources.getString(resource))
    }

    fun showMessageAsToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun listenForNetworkChanges() {
        val dis = fromLocalBroadcast(this, IntentFilter().apply {
            addAction(ACTION_CHANGE_NETWORK_STATE)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val state = it.getBooleanExtra(EXTRA_NETWORK_IS_CONNECTED, false)

                if (firstConnectionNotification && state) {
                    firstConnectionNotification = false
                    return@subscribe
                }

                if (state.not())
                    notifyNetworkDisconnection()
                else {
                    notifyNetworkConnection()
                    viewModel.onNetworkConnection()
                }
            }, {
                notifyNetworkDisconnection()
            })

        compositeDisposable.add(dis)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun notifyNetworkConnection() {
        viewNetFailureSnackBarWillAttachTo?.let {
            Snackbar.make(it, R.string.app_back_online, Snackbar.LENGTH_SHORT).also { snackBar ->
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_dark
                    )
                )
                buildDefaultSnackBarTextParams(snackBar)
            }.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun notifyNetworkDisconnection() {
        viewNetFailureSnackBarWillAttachTo?.let {
            Snackbar.make(
                it,
                R.string.app_no_internet_connection,
                Snackbar.LENGTH_SHORT
            ).also { snackBar ->
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_dark
                    )
                )
                buildDefaultSnackBarTextParams(snackBar)
            }.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun buildDefaultSnackBarTextParams(snackBar: Snackbar) =
        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.dimen_16_sp))
        }

    abstract fun attachViewModelToView()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    companion object {
        private var firstConnectionNotification = true
    }
}

