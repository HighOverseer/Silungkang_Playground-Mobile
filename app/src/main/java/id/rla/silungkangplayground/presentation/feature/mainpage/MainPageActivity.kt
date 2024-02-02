package id.rla.silungkangplayground.presentation.feature.mainpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.ActivityMainpageBinding

class MainPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainpageBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initBottomNavigation()
    }


    private fun initBottomNavigation() {
        //for applying the selector drawable for the menu icon
        binding.content.bottomNavigation.itemIconTintList = null

        //setting up bottom navigation with NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.content.bottomNavigation.setupWithNavController(navController)
    }
}