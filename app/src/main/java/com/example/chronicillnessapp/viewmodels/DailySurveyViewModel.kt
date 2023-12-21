package com.example.chronicillnessapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chronicillnessapp.data.DailySurvey

class DailySurveyViewModel : ViewModel() {
    private val _dailySurvey = MutableLiveData<DailySurvey>()

    // Expose LiveData to observe changes
    val dailySurvey: LiveData<DailySurvey>
        get() = _dailySurvey

    // Function to update the LiveData
    fun updateDailySurvey(newDailySurvey: DailySurvey) {
        _dailySurvey.value = newDailySurvey
    }
}