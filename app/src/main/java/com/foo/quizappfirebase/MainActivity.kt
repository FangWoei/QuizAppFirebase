package com.foo.quizappfirebase

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.model.Role
import com.foo.quizappfirebase.data.repo.UserRepo
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authService: AuthService
    @Inject
    lateinit var userRepo: UserRepo
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboardFragment, R.id.studentHomeFragment), drawerLayout
        )

        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setupWithNavController(navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)

        if (authService.getUid() != null) {
            navigateDashboard()
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            if (dest.id == R.id.loginRegisterFragment) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }

        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            authService.logOut()
            navigateToNavView()
            drawerLayout.close()
            true
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    private fun navigateDashboard() {
        lifecycleScope.launch {
            val user = userRepo.getUser()
            if (user != null) {
                navController.navigate(
                    if (user.role == Role.TEACHER) {
                        R.id.dashboardFragment
                    } else {
                        R.id.studentHomeFragment
                    },
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.loginRegisterFragment, true)
                        .build()
                )
            }
        }
    }


    private fun navigateToNavView() {
      lifecycleScope.launch {
          navController.navigate(
              R.id.loginRegisterFragment,
              null,
              NavOptions.Builder()
                  .setPopUpTo(navController.graph.startDestinationId, true)
                  .build()
          )
      }
    }

}