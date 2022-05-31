package com.example.moviemvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviemvi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupSmoothBottomMenu(navController)
        navController.addOnDestinationChangedListener { _, destiantion, _ ->
            when (destiantion.id) {
                R.id.mainFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.bookmarkFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.cabinetFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.blankFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }



    }
    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment_container).navigateUp()
    }

    private fun setupSmoothBottomMenu(navController: NavController) {
        val popupMenu = androidx.appcompat.widget.PopupMenu(this, binding.bottomNavigation)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        binding.bottomNavigation.setupWithNavController(menu, navController)
    }
}