package id.rla.silungkangplayground.presentation.feature.mainpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.FragmentActivityCallback
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityMainpageBinding
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.event.EventFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.info.InfoFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.playground.PlaygroundFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.VoucherFragment

class MainPageActivity : AppCompatActivity(), FragmentActivityCallback {

    private lateinit var binding: ActivityMainpageBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initBottomNavigation()
    }

    override fun keepBottomNavSelected(menuId: Int) {
        binding.content.bottomNavigation.apply {
            when (menuId) {
                R.id.menu_event, R.id.menu_info, R.id.menu_playground, R.id.menu_voucher -> {
                    val menuItem = menu.findItem(menuId)
                    menuItem.isChecked = true
                }
            }
        }
    }

    private fun getCurrentFragment(): Fragment {
        return navHostFragment.childFragmentManager.fragments[0]
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
                currFragment !is InfoFragment &&
                currFragment !is VoucherFragment &&
                currFragment !is PlaygroundFragment){

                currFragment.findNavController().popBackStack()
            }
        }
    }
}