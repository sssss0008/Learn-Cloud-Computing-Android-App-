package com.example.ui.screens.practice

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.example.data.repository.CloudReferenceData
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun PracticeCenterScreen(
    onStartMockExam: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Cloud Certification Practice Center",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Prepare for Solutions Architect, DevOps & SysOps exams",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Mock Exam Hero Card
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, ElectricCyan)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("FULL MOCK EXAM SIMULATOR", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    StatusBadge("Ready")
                }

                Text(
                    text = "Cloud Solutions Architect Mock Exam",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Slate100
                )

                Text(
                    text = "Timed exam session covering High Availability, Security & IAM, VPC Networking, and Distributed Data Storage.",
                    fontSize = 13.sp,
                    color = Slate300
                )

                Button(
                    onClick = onStartMockExam,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Mock Exam", fontWeight = FontWeight.Bold)
                }
            }
        }

        Text("PRACTICE TRACKS", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

        PracticeTrackCard("Cloud Fundamentals Track", "Basic compute, storage, NIST definitions, and billing", "40 Questions", Icons.Default.Cloud)
        PracticeTrackCard("DevOps & CI/CD Track", "Docker, Kubernetes, GitOps, and pipeline automation", "35 Questions", Icons.Default.LinearScale)
        PracticeTrackCard("Security & IAM Architecture", "Least privilege, STS, encryption, and threat modeling", "30 Questions", Icons.Default.Security)
        PracticeTrackCard("High Availability & DR", "RPO, RTO, multi-region replication, and failovers", "25 Questions", Icons.Default.AccountTree)
    }
}

@Composable
private fun PracticeTrackCard(title: String, subtitle: String, count: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CyberBorder)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DarkNavyCardElevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate100)
                Text(subtitle, fontSize = 11.sp, color = Slate400)
            }
            Text(count, fontSize = 11.sp, color = CloudBlueLight, fontFamily = FontFamily.Monospace)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockExamScreen(
    onBack: () -> Unit
) {
    val questions = CloudReferenceData.mockExamQuestions
    var currentIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() }
    var isSubmitted by remember { mutableStateOf(false) }

    val currentQ = questions[currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Architecture Exam Simulator", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isSubmitted) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("QUESTION ${currentIndex + 1} OF ${questions.size}", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Text(currentQ.domain, color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(currentQ.question, fontSize = 14.sp, color = Slate100, lineHeight = 20.sp)
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        currentQ.options.forEachIndexed { idx, opt ->
                            val isSelected = userAnswers[currentIndex] == idx
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { userAnswers[currentIndex] = idx }
                                    .border(1.dp, if (isSelected) ElectricCyan else CyberBorder, RoundedCornerShape(10.dp)),
                                color = if (isSelected) DarkNavyCardElevated else DarkNavyCard
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { userAnswers[currentIndex] = idx },
                                        colors = RadioButtonDefaults.colors(selectedColor = ElectricCyan)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(opt, fontSize = 13.sp, color = Slate200)
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { if (currentIndex > 0) currentIndex-- },
                        enabled = currentIndex > 0,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Previous")
                    }

                    if (currentIndex < questions.size - 1) {
                        Button(
                            onClick = { currentIndex++ },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Next")
                        }
                    } else {
                        Button(
                            onClick = { isSubmitted = true },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StatusHealthy),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Submit Exam")
                        }
                    }
                }
            } else {
                // Exam Results
                val correctCount = questions.indices.count { userAnswers[it] == questions[it].correctIndex }
                val scorePercent = (correctCount.toFloat() / questions.size) * 100

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(if (scorePercent >= 70) StatusHealthy else StatusWarning),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (scorePercent >= 70) Icons.Default.Check else Icons.Default.Refresh,
                            contentDescription = null,
                            tint = DeepNavy,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Text(
                        text = if (scorePercent >= 70) "EXAM PASSED!" else "REVISION RECOMMENDED",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (scorePercent >= 70) StatusHealthy else StatusWarning
                    )

                    Text(
                        text = "Score: ${scorePercent.toInt()}% ($correctCount / ${questions.size} correct)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100,
                        fontFamily = FontFamily.Monospace
                    )

                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Return to Practice Center")
                    }
                }
            }
        }
    }
}
