package com.example.ui.screens.roadmap

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CloudRoadmapData
import com.example.ui.theme.*

@Composable
fun RoadmapScreen(
    completedLessonCount: Int,
    onSelectStep: (stepIndex: Int) -> Unit
) {
    val steps = CloudRoadmapData.roadmapSteps

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                text = "Cloud Engineering Roadmap",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Zero → Foundations → Cloud Services → Architecture → Security → DevOps → Cloud Engineer",
                style = MaterialTheme.typography.bodySmall,
                color = ElectricCyan,
                fontFamily = FontFamily.Monospace
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            itemsIndexed(steps) { index, (title, desc) ->
                val isCompleted = completedLessonCount >= (index + 1)
                val isCurrent = completedLessonCount == index

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectStep(index) }
                            .background(if (isCurrent) DarkNavyCardElevated else DarkNavyCard)
                            .border(
                                1.dp,
                                if (isCurrent) ElectricCyan else if (isCompleted) StatusHealthy else CyberBorder,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isCompleted) StatusHealthy
                                    else if (isCurrent) ElectricCyan
                                    else Slate800
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = DeepNavy, modifier = Modifier.size(16.dp))
                            } else {
                                Text(
                                    "${index + 1}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) DeepNavy else Slate300,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isCurrent) ElectricCyan else if (isCompleted) StatusHealthy else Slate100
                            )
                            Text(
                                text = desc,
                                fontSize = 12.sp,
                                color = Slate400,
                                lineHeight = 16.sp
                            )
                        }

                        if (title == "CLOUD ENGINEER") {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = ElectricCyan)
                        }
                    }

                    if (index < steps.size - 1) {
                        Box(
                            modifier = Modifier
                                .padding(start = 28.dp)
                                .height(14.dp)
                                .width(2.dp)
                                .background(if (isCompleted) StatusHealthy else Slate700)
                        )
                    }
                }
            }
        }
    }
}
