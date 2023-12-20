package com.example.chronicillnessapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.chronicillnessapp.R
import com.example.chronicillnessapp.databinding.FragmentHomeBinding
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter


class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_home, container, false)

        setBarChartValues()
        setProgressBar()

        return homeBinding.root
    }

    private fun setBarChartValues() {
        val xValues = ArrayList<String>()
        xValues.add("Pain Level")
        xValues.add("Fatigue Level")
        xValues.add("Sleep Quality")
        xValues.add("Mood")
        xValues.add("Physical Activity")

        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(0f, 2f))
        barEntries.add(BarEntry(1f, 5f))
        barEntries.add(BarEntry(2f, 3f))
        barEntries.add(BarEntry(3f, 6f))
        barEntries.add(BarEntry(4f, 1f))

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
        limitLine3.lineColor = Color.YELLOW
        limitLine3.lineWidth = 0.5f

        val limitLine4 = LimitLine(4f, "")
        limitLine4.lineColor = Color.GREEN
        limitLine4.lineWidth = 0.5f

        val limitLine5 = LimitLine(5f, "")
        limitLine5.lineColor = Color.GREEN
        limitLine5.lineWidth = 0.5f

        val limitLine6 = LimitLine(6f, "")
        limitLine6.lineColor = Color.GREEN
        limitLine6.lineWidth = 0.5f

        val limitLine7 = LimitLine(7f, "")
        limitLine7.lineColor = Color.GREEN
        limitLine7.lineWidth = 0.5f

        homeBinding.barChart.apply {
            data = barChartData
            axisRight.isEnabled = false
            axisLeft.apply {
                isEnabled = true
                axisMinimum = 0f
                axisMaximum = 7f
                addLimitLine(limitLine1)
                addLimitLine(limitLine2)
                addLimitLine(limitLine3)
                addLimitLine(limitLine4)
                addLimitLine(limitLine5)
                addLimitLine(limitLine6)
                addLimitLine(limitLine7)

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

}