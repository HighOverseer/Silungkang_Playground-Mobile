package id.rla.silungkangplayground.presentation.feature.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityLoginBinding
import id.rla.silungkangplayground.presentation.util.obtainViewModel
import id.rla.silungkangplayground.presentation.util.showToast
import id.rla.silungkangplayground.presentation.feature.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(applicationContext)
        initButtons()
        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
            binding.apply {
                isLoading.observe(this@LoginActivity){isLoading ->
                    progressBar.isVisible = isLoading
                    buttonLogin.isEnabled = !isLoading

                }

                toastMessage.observe(this@LoginActivity){
                    it.getContentIfNotHandled()?.let { stringRes ->
                        showToast(stringRes)
                    }
                }

                userAuthenticatedEvent.observe(this@LoginActivity){
                    it.getContentIfNotHandled()?.let {
                        buttonLogin.isEnabled = false
                        setResult(DashboardActivity.LOGIN_SUCCESS_RESULT_CODE)
                        finish()
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
            val memberId = acetMemberId.text.toString().trim()
            val password = acetPassword.text.toString().trim()

            if (memberId.isEmpty() || password.isEmpty()) return@apply

            viewModel.login(
                memberId,
                password
            )
            return
        }

        showToast(getString(R.string.usename_atau_password_tidak_boleh_kosong))
    }

}