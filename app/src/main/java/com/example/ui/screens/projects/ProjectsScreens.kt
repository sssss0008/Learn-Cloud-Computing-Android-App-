package com.example.ui.screens.projects

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CloudProject
import com.example.data.repository.CloudProjectsData
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun ProjectsHubScreen(
    completedProjectIds: Set<String>,
    onSelectProject: (projectId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Real-World Cloud Projects",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Production architectures with requirements, topologies, and validation",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(CloudProjectsData.projects) { project ->
                val isCompleted = completedProjectIds.contains(project.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectProject(project.id) }
                        .border(
                            1.dp,
                            if (isCompleted) StatusHealthy.copy(alpha = 0.5f) else CyberBorder,
                            RoundedCornerShape(14.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(project.difficulty, color = ElectricCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                Text("•", color = Slate500, fontSize = 10.sp)
                                Text(project.timeEstimate, color = Slate400, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                            }
                            if (isCompleted) {
                                StatusBadge("Passed")
                            }
                        }

                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate100
                        )

                        Text(
                            text = project.objective,
                            fontSize = 12.sp,
                            color = Slate300,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            project.concepts.forEach { concept ->
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Slate800
                                ) {
                                    Text(
                                        text = concept,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        fontSize = 10.sp,
                                        color = Slate200,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    projectId: String,
    isCompleted: Boolean,
    onCompleteProject: () -> Unit,
    onBack: () -> Unit
) {
    val project = CloudProjectsData.projects.find { it.id == projectId } ?: CloudProjectsData.projects.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(project.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isCompleted) {
                        StatusBadge("Passed", modifier = Modifier.padding(end = 8.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Objective Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("PROJECT OBJECTIVE & ARCHITECTURE", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text(project.objective, fontSize = 14.sp, color = Slate100, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Topology Flow: ${project.architectureSummary}", fontSize = 12.sp, color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                }
            }

            // Requirements
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("SYSTEM REQUIREMENTS", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    project.requirements.forEach { req ->
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("•", color = ElectricCyan, fontWeight = FontWeight.Bold)
                            Text(req, fontSize = 13.sp, color = Slate200)
                        }
                    }
                }
            }

            // Implementation Steps
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("IMPLEMENTATION PROCEDURE", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    project.steps.forEach { step ->
                        Text(step, fontSize = 13.sp, color = Slate300, lineHeight = 18.sp)
                    }
                }
            }

            // Project Score Breakdown
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("PROJECT EVALUATION CRITERIA", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    ScoreMetricRow("Architecture Design", "95 / 100")
                    ScoreMetricRow("Security & Least Privilege", "90 / 100")
                    ScoreMetricRow("Scalability & Resilience", "92 / 100")
                    ScoreMetricRow("Reliability & Health Checks", "96 / 100")
                }
            }

            Button(
                onClick = onCompleteProject,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isCompleted) StatusHealthy else CloudBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isCompleted) "Project Completed (Tap to review)" else "Mark Project as Completed", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ScoreMetricRow(label: String, score: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 13.sp, color = Slate200)
        Text(score, fontSize = 12.sp, color = StatusHealthy, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
    }
}
