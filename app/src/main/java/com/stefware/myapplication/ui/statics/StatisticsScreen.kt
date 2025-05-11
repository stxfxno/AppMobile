package com.stefware.myapplication.ui.statics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel(),
    navigateToTimeline: () -> Unit
) {
    val statistics by viewModel.statistics.collectAsState()
    val members by viewModel.members.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Project Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = navigateToTimeline,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("View Timeline")
        }

        // Bar Chart for Sprint Progress
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = 8.dp)
        ) {
            AndroidView(
                factory = { context ->
                    BarChart(context).apply {
                        description.isEnabled = false
                        setDrawGridBackground(false)
                        legend.isEnabled = true

                        // Configure X and Y axes
                        xAxis.setDrawGridLines(false)
                        axisLeft.setDrawGridLines(true)
                        axisRight.isEnabled = false

                        // Set data
                        val entries1 = statistics.map { stat ->
                            BarEntry(stat.sprint.toFloat(), stat.completedPercentage.toFloat())
                        }

                        val entries2 = statistics.map { stat ->
                            BarEntry(stat.sprint.toFloat(), stat.inProgressPercentage.toFloat())
                        }

                        val dataSet1 = BarDataSet(entries1, "Completed")
                        dataSet1.color = android.graphics.Color.GREEN

                        val dataSet2 = BarDataSet(entries2, "In Progress")
                        dataSet2.color = android.graphics.Color.RED

                        val barData = BarData(dataSet1, dataSet2)
                        data = barData

                        invalidate() // Refresh chart
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Members List
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Team Members",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                members.forEach { member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        // Member icon or avatar
                        // ...

                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(text = member.fullName)
                            Text(text = member.role)
                        }
                    }
                }
            }
        }
    }
}