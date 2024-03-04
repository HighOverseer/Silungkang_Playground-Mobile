package id.rla.silungkangplayground.presentation.feature.mainpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.FragmentActivityCallback
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityMainpageBinding
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.presentation.customview.MemberAccountPopup
import id.rla.silungkangplayground.presentation.feature.dashboard.DashboardActivity
import id.rla.silungkangplayground.presentation.feature.feedback.FeedbackAppreciationDialogFragment
import id.rla.silungkangplayground.presentation.feature.feedback.FeedbackDialogFragment
import id.rla.silungkangplayground.presentation.feature.feedback.OnSendFeedbackListener
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.event.EventFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.card_member.CardMemberFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.playground.PlaygroundFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.VoucherFragment
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.makeToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainPageActivity : AppCompatActivity(), FragmentActivityCallback, OnSendFeedbackListener {

    private lateinit var binding: ActivityMainpageBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment:NavHostFragment
    private val viewModel:MainPageViewModel by viewModels()
    private var toastMessage:Toast? = null

    private val memberAccountPopup by lazy {
        MemberAccountPopup(
            layoutInflater,
            viewModel::logout,
            viewModel::changeCurrentMemberAccount
        )
    }

    private val pageManu = listOf(
        R.id.menu_event,
        R.id.menu_card_member,
        R.id.menu_voucher,
        R.id.menu_playground
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initViewComponent()

        collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){
            when(it){
                is UIEvent.ToastMessageEvent -> {
                    showToastEvent(it.message)
                }
                is UIEvent.OnUserRequiredToLoginEvent -> {
                    delay(500L)
                    viewModel.logout()
                }
                is UIEvent.OnUserSuccessfullyLogout -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is UIEvent.OnUserSuccessfullyChangeAccount ->{
                    if (navController.currentDestination?.id == R.id.menu_voucher) return@collectChannelFlowOnLifecycleStarted

                    if (navController.currentDestination?.id in pageManu){
                        navController.popBackStack()
                    }else navController.navigateUp()
                }
                is UIEvent.UserFeedbackEvent -> {
                    showFeedbackFragment()
                }

                is UIEvent.OnUserFeedbackSentEvent -> {
                    showFeedbackAppreciationFragment()
                }
                else -> Unit
            }
        }
    }

    private fun showFeedbackAppreciationFragment() {
        val fragment = FeedbackAppreciationDialogFragment()
        fragment.show(getCurrentFragment().childFragmentManager, null)
    }

    private fun showToastEvent(message:StringRes) {
        toastMessage?.cancel()
        toastMessage = makeToast(message)
        toastMessage?.show()
    }

    private fun initViewComponent() {
        initBottomNavigation()
        initAccountMemberPopup()
    }


    private fun initAccountMemberPopup(){
        binding.apply {
            collectLatestOnLifeCycleStarted(viewModel.uiState){
                content.progressBar.isVisible = it.isLoading
                content.navHostFragment.isVisible = !it.isLoading || it.currentMemberAccountId != null

                civAccountProfileIcon.isClickable = !it.isLoading

                val currentMemberAccount:MemberAccount?
                val listMemberAccount:List<MemberAccount>

                withContext(Dispatchers.Default){
                    currentMemberAccount = it.listMemberAccount.find { acc -> acc.id == it.currentMemberAccountId }

                    if (currentMemberAccount == null) {
                        listMemberAccount = emptyList()
                        return@withContext
                    }

                    listMemberAccount = mutableListOf(currentMemberAccount)
                    listMemberAccount.addAll(it.listMemberAccount.filter { acc -> acc.id != currentMemberAccount.id })
                }

                actvCurrentActiveMember.text = currentMemberAccount?.name ?: ""
                memberAccountPopup.updateListMemberAccount(listMemberAccount)
            }

            civAccountProfileIcon.setOnClickListener {
                if (memberAccountPopup.isPopUpShown){

                    memberAccountPopup.dismissPopup()
                    return@setOnClickListener
                }

                memberAccountPopup.showPopup(binding.civAccountProfileIcon)
            }
        }
    }

    override fun keepBottomNavSelected(menuId: Int) {
        binding.content.bottomNavigation.apply {
            when (menuId) {
                R.id.menu_event, R.id.menu_card_member, R.id.menu_playground, R.id.menu_voucher -> {
                    val menuItem = menu.findItem(menuId)
                    menuItem.isChecked = true
                }
            }
        }
    }

    private fun getCurrentFragment(): Fragment {
        return navHostFragment.childFragmentManager.fragments[0]
    }

    private fun showFeedbackFragment(){
        val fragment = FeedbackDialogFragment()
        fragment.show(getCurrentFragment().childFragmentManager, null)
    }
    override fun sendFeedback(rating: Int, content: String, isSubmitted: Boolean) {
        viewModel.sendFeedback(rating, content, isSubmitted)
    }

    private fun initBottomNavigation() {
        //for applying the selector drawable for the menu icon
        binding.content.bottomNavigation.itemIconTintList = null

        //setting up bottom navigation with NavController
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.content.bottomNavigation.setupWithNavController(navController)

        binding.content.bottomNavigation.setOnItemReselectedListener {
            val currFragment = getCurrentFragment()
            if (currFragment !is EventFragment &&
                currFragment !is CardMemberFragment &&
                currFragment !is VoucherFragment &&
                currFragment !is PlaygroundFragment){

                currFragment.findNavController().popBackStack()
            }
        }
    }
}