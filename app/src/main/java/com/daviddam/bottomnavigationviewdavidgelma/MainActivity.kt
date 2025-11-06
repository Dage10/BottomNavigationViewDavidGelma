package com.daviddam.bottomnavigationviewdavidgelma

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


import com.daviddam.bottomnavigationviewdavidgelma.databinding.ActivityMainBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    private lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        //AFEGIT - Configuració del DrawerLayout
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setupWithNavController(navController)

        //AFEGIT - Toolbar i toggle
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //AFEGIT - Configuració de l’AppBar amb Drawer
        appBarConfig = AppBarConfiguration.Builder(
            R.id.favFragment, R.id.musicFragment, R.id.newsFragment, R.id.placesFragment
        ).setDrawerLayout(drawerLayout).build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
    }

    //AFEGIT - Permet que funcioni el botó de la toolbar
    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment).navController
        return NavigationUI.navigateUp(navController, appBarConfig)
    }
}