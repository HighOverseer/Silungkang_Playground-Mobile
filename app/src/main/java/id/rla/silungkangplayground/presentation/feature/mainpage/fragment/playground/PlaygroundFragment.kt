package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.rla.silungkangplayground.databinding.FragmentPlaygroundBinding

class PlaygroundFragment : Fragment() {

    private var binding: FragmentPlaygroundBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaygroundBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}