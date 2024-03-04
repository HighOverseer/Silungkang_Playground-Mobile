package id.rla.silungkangplayground.presentation.feature.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityDashboardBinding
import id.rla.silungkangplayground.domain.helper.Dummy.getListEventSlider
import id.rla.silungkangplayground.domain.model.CreativeCorner
import id.rla.silungkangplayground.presentation.feature.dashboard.adapter.CreativeCornerAdapter
import id.rla.silungkangplayground.presentation.feature.dashboard.adapter.DashboardEventSliderAdapter
import id.rla.silungkangplayground.presentation.feature.login.LoginActivity
import id.rla.silungkangplayground.presentation.feature.mainpage.MainPageActivity
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDashboardBinding
    private lateinit var slideEventAdapter: DashboardEventSliderAdapter
    private val viewModel:DashboardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
        initAdapters()
        initButtons()
    }

    private val onPreDrawListener = object:OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            return when (viewModel.isUserHasAlreadyLoggedIn) {
                true -> {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    goToMainPage()
                    true
                }
                false -> {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                }
                null -> false
            }
        }
    }

    private fun goToMainPage(){
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initButtons() {
        binding.apply {
            acibSlideRight.setOnClickListener {
                rvSlideEvent.scrollToNextItem()
            }

            acibSlideLeft.setOnClickListener {
                rvSlideEvent.scrollToPreviousItem()
            }

            acbLogin.setOnClickListener {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun initAdapters(){
        binding.apply {
            slideEventAdapter = DashboardEventSliderAdapter(getListEventSlider())
            rvSlideEvent.adapter = slideEventAdapter
            rvSlideEvent.layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            rvSlideEvent.setSlideAutomatically(this@DashboardActivity)

            rvCreativeCorner.adapter = CreativeCornerAdapter(getListCreativeCorners())
            rvCreativeCorner.addItemDecoration(GenericItemDecoration(resources.displayMetrics, paddingEnd = 10))
            rvCreativeCorner.layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)


        }
    }


    private fun getListCreativeCorners():List<CreativeCorner>{
        return listOf(
            CreativeCorner(
                getString(R.string.tahfiz_untuk_anak),
                R.drawable.ic_tahfiz
            ), CreativeCorner(
                getString(R.string.magenetik_drawing_board),
                R.drawable.ic_canvas
            ), CreativeCorner(
                title = getString(R.string.puzzle_tetris),
                icon = R.drawable.ic_puzzle
            )
        )
    }

    companion object{
        const val LOGIN_SUCCESS_RESULT_CODE = 200
    }

}