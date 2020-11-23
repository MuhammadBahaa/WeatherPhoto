package muhammad.bahaa.robustatask.ui.home


import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.databinding.ActivityHomeBinding
import muhammad.bahaa.robustatask.ui.base.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(){

    override val rootView: Int
        get() = R.layout.activity_home
    override val vMType: Class<HomeViewModel> = HomeViewModel::class.java

    override fun attachViewModelToView() {
        viewDataBinding.homeViewModel = viewModel
        viewDataBinding.lifecycleOwner = this
    }
}