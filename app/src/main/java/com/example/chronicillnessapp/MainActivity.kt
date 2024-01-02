package com.example.chronicillnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.chronicillnessapp.data.DailySurvey
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
import com.example.chronicillnessapp.interfaces.MainActivityToHomeFragmentInterface
import com.example.chronicillnessapp.viewmodels.DailySurveyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainActivityToHomeFragmentInterface {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var dailySurveyViewModel: DailySurveyViewModel
    var selectedPainLevel = 1
    var selectedFatigueLevel = 1
    var selectedSleepQuality = 1
    var selectedMood = 1
    var selectedPhysicalActivities = 1


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
        //openFragment(HomeFragment())
        if (savedInstanceState == null) {
            // Only add the HomeFragment if it's the initial creation of the activity
            openFragment(HomeFragment())
        }

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 10
            this.state = BottomSheetBehavior.STATE_COLLAPSED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> binding.fab.hide()
                        BottomSheetBehavior.STATE_COLLAPSED -> binding.fab.show()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }

        binding.fab.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }

        //initialize ViewModel
        dailySurveyViewModel = ViewModelProvider(this).get(DailySurveyViewModel::class.java)

        //Observe LiveData
        dailySurveyViewModel.dailySurvey.observe(this) { dailySurvey ->
            Log.d("MainActivity", "Observer triggered with data: $dailySurvey")
            updateUI(dailySurvey)
        }

        
        binding.painLevelRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedPainLevel = radioButton.text.toString().toInt()
        }

        binding.fatigueLevelRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedFatigueLevel = radioButton.text.toString().toInt()
        }

        binding.sleepQualityRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedSleepQuality = radioButton.text.toString().toInt()
        }

        binding.moodRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedMood = radioButton.text.toString().toInt()
        }

        binding.physicalActivitiesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedPhysicalActivities = radioButton.text.toString().toInt()
        }
        
        binding.saveBtn.setOnClickListener {
            val newDailySurvey = DailySurvey(
                painLevel = selectedPainLevel,
                fatigueLevel = selectedFatigueLevel,
                sleepQuality = selectedSleepQuality,
                mood = selectedMood,
                physicalActivities = selectedPhysicalActivities
            )
            Log.d("Sleep Quality", selectedSleepQuality.toString())
            dailySurveyViewModel.updateDailySurvey(newDailySurvey)
            //updateUI(newDailySurvey)
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
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
        fragmentTransaction.replace(R.id.fragment_container, fragment, HomeFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun updateUI(dailySurvey: DailySurvey) {
        Log.d("MainActivity", "updateUI called with data: $dailySurvey")
        // Find the HomeFragment
        val homeFragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as? HomeFragment

        Log.d("MainActivity-homefragment", homeFragment.toString())
        // Update the UI in the HomeFragment
        homeFragment?.updateUI(dailySurvey)
    }


}