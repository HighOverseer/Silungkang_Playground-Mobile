package id.rla.silungkangplayground.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import id.rla.silungkangplayground.di.Injection
import id.rla.silungkangplayground.data.local.dataStore
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.presentation.feature.login.LoginViewModel
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.VoucherViewModel

class ViewModelFactory private constructor(
    private val repository: Repository
):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return modelClass.run {
            when{
                isAssignableFrom(LoginViewModel::class.java) -> {
                    LoginViewModel(repository) as T
                }
                isAssignableFrom(VoucherViewModel::class.java)->{
                    VoucherViewModel(repository) as T
                }
                else -> return super.create(modelClass, extras)
            }
        }

    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory?=null

        fun getInstance(applicationContext: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(
                        applicationContext.dataStore
                    )
                )
            }.also { INSTANCE = it }
        }
    }
}