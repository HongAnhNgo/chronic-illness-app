package com.example.chronicillnessapp.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.chronicillnessapp.R
import com.example.chronicillnessapp.data.DailySurvey
import com.example.chronicillnessapp.databinding.FragmentHomeBinding
import com.example.chronicillnessapp.interfaces.MainActivityToHomeFragmentInterface
import com.example.chronicillnessapp.viewmodels.DailySurveyViewModel
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter


class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var dailySurveyViewModel: DailySurveyViewModel
    private var communicationInterface: MainActivityToHomeFragmentInterface? = null

    companion object {
        const val TAG = "HomeFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_home, container, false)
        homeBinding.healthScoreDiagnosis.text = "You haven’t record your health today! It’s time for your daily health check up!"


        setBarChartValues(0, 0, 0, 0, 0)
        setProgressBar()




        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailySurveyViewModel = ViewModelProvider(requireActivity()).get(DailySurveyViewModel::class.java)

        // Observe the latestDailySurvey LiveData
        dailySurveyViewModel.dailySurvey.observe(viewLifecycleOwner) { dailySurvey ->
            dailySurvey?.let { updateUI(it) }
        }
    }


    private fun setBarChartValues(painLevel: Int, fatigueLevel: Int, sleepQuality: Int, mood: Int, physicalActivities: Int) {
        val xValues = ArrayList<String>()
        xValues.add("Pain Level")
        xValues.add("Fatigue Level")
        xValues.add("Sleep Quality")
        xValues.add("Mood")
        xValues.add("Physical Activity")

        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(0f, painLevel.toFloat()))
        barEntries.add(BarEntry(1f, fatigueLevel.toFloat()))
        barEntries.add(BarEntry(2f, sleepQuality.toFloat()))
        barEntries.add(BarEntry(3f, mood.toFloat()))
        barEntries.add(BarEntry(4f, physicalActivities.toFloat()))

        val barColors = ArrayList<Int>()
        for (xValue in xValues) {
            when(xValue) {
                "Pain Level" -> barColors.add(ContextCompat.getColor(requireContext(), R.color.colorPainLevel))
                "Fatigue Level" -> barColors.add(ContextCompat.getColor(requireContext(), R.color.colorFatigueLevel))
                "Sleep Quality" -> barColors.add(ContextCompat.getColor(requireContext(), R.color.colorSleepQuality))
                "Mood" -> barColors.add(ContextCompat.getColor(requireContext(), R.color.colorMood))
                "Physical Activity" -> barColors.add(ContextCompat.getColor(requireContext(), R.color.colorPhysicalActivity))
                else -> barColors.add(ContextCompat.getColor(requireContext(), R.color.md_theme_light_background))

            }
        }

        val barDataSet = BarDataSet(barEntries, "Bar Data")
        //barDataSet.color = resources.getColor(R.color.md_theme_light_primary)
        barDataSet.colors = barColors
        barDataSet.valueTextSize = 10f

        val barChartData = BarData(barDataSet)
        barChartData.barWidth = 0.8f

        val limitLine1 = LimitLine(1f, "")
        limitLine1.lineColor = Color.RED
        limitLine1.lineWidth = 0.5f

        val limitLine2 = LimitLine(2f, "")
        limitLine2.lineColor = Color.RED
        limitLine2.lineWidth = 0.5f

        val limitLine3 = LimitLine(3f, "")
        limitLine3.lineColor = Color.RED
        limitLine3.lineWidth = 0.5f

        val limitLine4 = LimitLine(4f, "")
        limitLine4.lineColor = Color.YELLOW
        limitLine4.lineWidth = 0.5f

        val limitLine5 = LimitLine(5f, "")
        limitLine5.lineColor = Color.YELLOW
        limitLine5.lineWidth = 0.5f

        val limitLine6 = LimitLine(6f, "")
        limitLine6.lineColor = Color.YELLOW
        limitLine6.lineWidth = 0.5f

        val limitLine7 = LimitLine(7f, "")
        limitLine7.lineColor = Color.YELLOW
        limitLine7.lineWidth = 0.5f

        val limitLine8 = LimitLine(8f, "")
        limitLine8.lineColor = Color.GREEN
        limitLine8.lineWidth = 0.5f

        val limitLine9 = LimitLine(9f, "")
        limitLine9.lineColor = Color.GREEN
        limitLine9.lineWidth = 0.5f

        val limitLine10 = LimitLine(10f, "")
        limitLine10.lineColor = Color.GREEN
        limitLine10.lineWidth = 0.5f

        homeBinding.barChart.apply {
            data = barChartData
            axisRight.isEnabled = false
            axisLeft.apply {
                isEnabled = true
                axisMinimum = 0f
                axisMaximum = 10f
                addLimitLine(limitLine1)
                addLimitLine(limitLine2)
                addLimitLine(limitLine3)
                addLimitLine(limitLine4)
                addLimitLine(limitLine5)
                addLimitLine(limitLine6)
                addLimitLine(limitLine7)
                addLimitLine(limitLine8)
                addLimitLine(limitLine9)
                addLimitLine(limitLine10)
            }

            xAxis.apply {
                valueFormatter = CustomXAxisValueFormatter(xValues)
                isGranularityEnabled = true
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
            }

            //barChart.setBackgroundColor(resources.getColor(R.color.md_theme_light_primary))
            animateXY(2000, 2000)

            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            description = null
            legend.isEnabled = false
        }

    }

    private class CustomXAxisValueFormatter(private val labels: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return if (value >= 0 && value < labels.size) {
                labels[value.toInt()]
            } else {
                ""
            }
        }
    }

    private fun setProgressBar() {
        var chronicDiagnosis = 55
        homeBinding.healthProgressBar.progress = chronicDiagnosis
        homeBinding.healthProgressBar.progressDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.custom_progress_bar)

        when {
            chronicDiagnosis <= 50 -> {
                homeBinding.healthProgressBar.progressDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.custom_progress_bar)
            }
            chronicDiagnosis <= 80 -> {
                homeBinding.healthProgressBar.progressDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.custom_progress_bar)

            }
            else -> {
                homeBinding.healthProgressBar.progressDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.custom_progress_bar)
            }
        }
    }

    fun updateUI(dailySurvey: DailySurvey) {
        // Update UI elements here
        // For example: textViewPainLevel.text = "Pain Level: ${dailySurvey.painLevel}"
        Log.d("HomeFragment", "updateUI called with data: $dailySurvey")
        if(dailySurvey.painLevel==0 && dailySurvey.fatigueLevel==0 && dailySurvey.sleepQuality==0 && dailySurvey.mood==0 && dailySurvey.physicalActivities==0) {
            homeBinding.healthScoreValue.text = "---"
            homeBinding.healthScoreDiagnosis.text = "You haven’t record your health today! It’s time for your daily health check up!"
        } else {
            val healthScore = (dailySurvey.painLevel+dailySurvey.fatigueLevel+dailySurvey.sleepQuality+dailySurvey.mood+dailySurvey.physicalActivities)*2
            homeBinding.healthScoreValue.text = healthScore.toString()
            if (healthScore<=40) {
                homeBinding.healthScoreDiagnosis.text = "Looks like you are having a bad day. Don't worry, it's going to be better! You can check out the contents that we have curated in your Feed that may help brighten your day!"
            } else if (healthScore<=70) {
                homeBinding.healthScoreDiagnosis.text = "Looks like you are doing pretty well! Let's go! "
            } else {
                homeBinding.healthScoreDiagnosis.text = "Looks like you are doing very well today! Keep it up!"
            }
        }
        setBarChartValues(dailySurvey.painLevel, dailySurvey.fatigueLevel, dailySurvey.sleepQuality, dailySurvey.mood, dailySurvey.physicalActivities)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityToHomeFragmentInterface) {
            communicationInterface = context
        } else {
            throw RuntimeException("$context must implement MainActivityToHomeFragmentInterface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        communicationInterface = null
    }

}