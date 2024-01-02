package com.example.chronicillnessapp.interfaces

import com.example.chronicillnessapp.data.DailySurvey

interface MainActivityToHomeFragmentInterface {
    fun updateUI(dailySurvey: DailySurvey)
}