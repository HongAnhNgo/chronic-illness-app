package com.example.chronicillnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.chronicillnessapp.databinding.ActivityMainBinding
import com.example.chronicillnessapp.fragments.AnalyticsFragment
import com.example.chronicillnessapp.fragments.AssessmentFragment
import com.example.chronicillnessapp.fragments.DarkModeFragment
import com.example.chronicillnessapp.fragments.FeedFragment
import com.example.chronicillnessapp.fragments.HelpFragment
import com.example.chronicillnessapp.fragments.HomeFragment
import com.example.chronicillnessapp.fragments.LanguageFragment
import com.example.chronicillnessapp.fragments.PersonalInfoFragment
import com.example.chronicillnessapp.fragments.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open_drawer, R.string.close_drawer)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.bottom_analytics -> openFragment(AnalyticsFragment())
                R.id.bottom_for_you -> openFragment(FeedFragment())
                R.id.bottom_assessment -> openFragment(AssessmentFragment())
                R.id.bottom_home -> openFragment(HomeFragment())

            }
            true
        }

        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Open Survey Pop Up", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.personal_info -> openFragment(PersonalInfoFragment())
            R.id.dark_mode -> openFragment(DarkModeFragment())
            R.id.language -> openFragment(LanguageFragment())
            R.id.help -> openFragment(HelpFragment())
            R.id.settings -> openFragment(SettingsFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}