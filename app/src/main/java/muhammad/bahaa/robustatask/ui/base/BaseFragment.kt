package muhammad.bahaa.robustatask.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> :
    androidx.fragment.app.Fragment() {
    abstract val rootView: Int
    abstract val vMType: Class<VM>
    val viewModel: VM by lazy { ViewModelProviders.of(this).get(vMType) }
    lateinit var viewDataBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, rootView, container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attachViewModelToView()
        listenForTermination()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun listenForTermination() {
        viewModel.terminate.observe(viewLifecycleOwner, Observer { activity?.finish() })
    }

    abstract fun attachViewModelToView()

}
