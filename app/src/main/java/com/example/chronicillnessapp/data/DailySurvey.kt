package com.example.chronicillnessapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailySurvey(
    var painLevel: Int = 0,
    var fatigueLevel: Int = 0,
    var sleepQuality: Int = 0,
    var mood: Int = 0,
    var physicalActivities: Int = 0
) : Parcelable
