package com.example.toeic_app.presentation.statistic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.toArgb

@Composable
fun StatisticScreen(
    viewModel: StatisticViewModel = hiltViewModel()
) {
    val chartData by viewModel.chartData.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Completion Rate",
                color = Color(0xFFFFD700),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                "The chart reflects your completion progress by skills",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
        item {
            SpiderChart(
                data = chartData,
                labels = listOf("Overall", "Listening", "Reading", "Writing", "Speaking", "Vocabulary", "Grammar")
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProgressDetailSection()
        }
    }
}

@Composable
fun SpiderChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier.size(300.dp)
) {
    // ✅ LẤY MÀU TRƯỚC KHI VÀO CANVAS
    val onBackground = MaterialTheme.colorScheme.onBackground

    Canvas(modifier = modifier) {

        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2 * 0.7f
        val step = 360f / labels.size

        val levels = 4
        for (i in 1..levels) {
            val levelRadius = radius * (i / levels.toFloat())
            val path = Path()

            for (j in labels.indices) {
                val angle = Math.toRadians((step * j - 90).toDouble())
                val x = center.x + levelRadius * cos(angle).toFloat()
                val y = center.y + levelRadius * sin(angle).toFloat()
                if (j == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            path.close()

            drawPath(
                path = path,
                color = onBackground.copy(alpha = 0.3f),
                style = Stroke(width = 1.dp.toPx())
            )
        }

        for (j in labels.indices) {
            val angle = Math.toRadians((step * j - 90).toDouble())
            val x = center.x + radius * cos(angle).toFloat()
            val y = center.y + radius * sin(angle).toFloat()

            drawLine(
                color = onBackground.copy(alpha = 0.3f),
                start = center,
                end = Offset(x, y),
                strokeWidth = 1.dp.toPx()
            )

            val labelRadius = radius * 1.2f
            val labelX = center.x + labelRadius * cos(angle).toFloat()
            val labelY = center.y + labelRadius * sin(angle).toFloat()

            drawContext.canvas.nativeCanvas.drawText(
                labels[j],
                labelX,
                labelY,
                android.graphics.Paint().apply {
                    color = onBackground.toArgb()
                    textSize = 30f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        if (data.isNotEmpty()) {
            val path = Path()

            data.forEachIndexed { index, value ->
                val angle = Math.toRadians((step * index - 90).toDouble())
                val r = radius * value
                val x = center.x + r * cos(angle).toFloat()
                val y = center.y + r * sin(angle).toFloat()
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            path.close()

            drawPath(
                path = path,
                color = onBackground.copy(alpha = 0.5f),
                style = Stroke(width = 2.dp.toPx())
            )

            drawPath(
                path = path,
                color = onBackground.copy(alpha = 0.1f)
            )
        }
    }
}
@Composable
fun ProgressDetailSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(16.dp)
    ) {
        Text("Progress Detail", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6366F1))
        Text(
            "Help you track your learning performance and analyze progress through each skill easily",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Add more detail cards here if needed
        Text("Listening", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
