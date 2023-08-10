package com.example.taskmanager

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.e("ololo", "token: " + it)
        }

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.taskFragment,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val fragmentWithoutBottomNav = setOf(
            R.id.onBoardingFragment,
            R.id.phoneFragment,
            R.id.verifyFragment,
            R.id.authFragment
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (fragmentWithoutBottomNav.contains(destination.id)) {
                navView.isVisible = false
                supportActionBar?.hide()
            } else {
                navView.isVisible = true
                supportActionBar?.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser?.uid == null) {
            navController.navigate(R.id.authFragment)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragmentsWithKeyBackToExit = setOf(
            R.id.authFragment,
            R.id.onBoardingFragment,
            R.id.navigation_home
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (fragmentsWithKeyBackToExit.contains(destination.id)) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
            R.id.menu_exit -> {
                showAlertDialogExit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialogExit() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.menu_exit))
            .setMessage(getString(R.string.menu_exit_message)).setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                auth.signOut()
                navController.navigate(R.id.authFragment)
            }.setNegativeButton(getString(R.string.no)) { _, _ -> }.show()
    }
}