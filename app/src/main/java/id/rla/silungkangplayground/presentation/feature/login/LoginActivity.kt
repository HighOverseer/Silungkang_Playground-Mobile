package id.rla.silungkangplayground.presentation.feature.login

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityLoginBinding
import id.rla.silungkangplayground.presentation.util.showToast
import id.rla.silungkangplayground.presentation.feature.dashboard.DashboardActivity
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initObservers()
    }

    private fun initObservers() {
        binding.apply {
            collectLatestOnLifeCycleStarted(viewModel.uiState){ uiState ->
                progressBar.isVisible = uiState.isLoading
                buttonLogin.isEnabled = !uiState.isLoading

            }

            collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){event ->
                when(event){
                    is UIEvent.OnUserAuthenticatedEvent -> {
                        buttonLogin.isEnabled = false
                        setResult(DashboardActivity.LOGIN_SUCCESS_RESULT_CODE)
                        finish()
                    }
                    is UIEvent.ToastMessageEvent -> {
                        showToast(event.message)
                    }
                }
            }
        }
    }


    private fun initButtons() {
        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login(){
        binding.apply {
            viewModel.login(
                acetHpNumber.text.toString(),
                acetPassword.text.toString()
            )
        }
    }

}