package id.rla.silungkangplayground.presentation.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface BindingFragmentInterface<T:ViewBinding> {
    fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container:ViewGroup?
    ):T
}