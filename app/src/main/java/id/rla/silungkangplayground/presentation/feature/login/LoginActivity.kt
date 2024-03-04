package id.rla.silungkangplayground.presentation.feature.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityLoginBinding
import id.rla.silungkangplayground.presentation.util.showToast
import id.rla.silungkangplayground.presentation.feature.dashboard.DashboardActivity
import id.rla.silungkangplayground.presentation.feature.mainpage.MainPageActivity
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.isKeyboardOpen

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    private val phoneNumberRegex = Regex(PHONE_NUMBER_REGEX_PATTERN)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initObservers()

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }



    private fun initObservers() {
        binding.apply {
            acetHpNumber.setOnFocusChangeListener { _, isFocus ->
                if (isFocus) return@setOnFocusChangeListener

                val phoneNumber = acetHpNumber.text.toString()
                if (phoneNumberRegex.matches(phoneNumber)){
                    val stringBuilder = StringBuilder("62")
                    stringBuilder.append(phoneNumber.substring(1))
                    acetHpNumber.setText(stringBuilder.toString())
                }
            }

            collectLatestOnLifeCycleStarted(viewModel.uiState){ uiState ->
                progressBar.isVisible = uiState.isLoading
                buttonLogin.isEnabled = !uiState.isLoading

            }

            collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){event ->
                when(event){
                    is UIEvent.OnUserAuthenticatedEvent -> {
                        buttonLogin.isEnabled = false
                        goToMainPage()
                    }
                    is UIEvent.ToastMessageEvent -> {
                        showToast(event.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private val onBackPressedCallback = object:OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            goBackToDashboard()
        }
    }

    private fun goBackToDashboard(){
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainPage(){
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        onBackPressedCallback.remove()
        super.onDestroy()

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

    companion object{
        private const val PHONE_NUMBER_REGEX_PATTERN = "^(08)[1-9][0-9]{8,11}$"
    }

}