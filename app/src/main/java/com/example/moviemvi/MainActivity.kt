package com.example.moviemvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moviemvi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSmoothBottomMenu()        //bottom navigation



    }

    private fun setupSmoothBottomMenu() {
        navController = findNavController(R.id.nav_host_fragment_container)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.my_menu)
        val menu = popupMenu.menu

        binding.bottomNavigation.setupWithNavController(menu, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}