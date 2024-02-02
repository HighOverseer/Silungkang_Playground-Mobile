package id.rla.silungkangplayground.presentation.feature.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import id.rla.silungkangplayground.databinding.ActivityDashboardBinding
import id.rla.silungkangplayground.domain.helper.Dummy.getListCreativeCorners
import id.rla.silungkangplayground.domain.helper.Dummy.getListEventSlider
import id.rla.silungkangplayground.presentation.feature.dashboard.adapter.CreativeCornerAdapter
import id.rla.silungkangplayground.presentation.feature.dashboard.adapter.DashboardEventSliderAdapter
import id.rla.silungkangplayground.presentation.feature.login.LoginActivity
import id.rla.silungkangplayground.presentation.feature.mainpage.MainPageActivity
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDashboardBinding
    private lateinit var slideEventAdapter: DashboardEventSliderAdapter


    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->

        if (result.resultCode == LOGIN_SUCCESS_RESULT_CODE){
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapters()
        initButtons()
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
        loginLauncher.launch(intent)
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

    companion object{
        const val LOGIN_SUCCESS_RESULT_CODE = 200
    }

}