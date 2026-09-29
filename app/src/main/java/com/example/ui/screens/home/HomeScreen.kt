package com.example.ui.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.data.repository.CloudCoursesData
import com.example.data.repository.CloudLabsData
import com.example.data.repository.CloudProjectsData
import com.example.ui.components.FreeBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import java.util.Calendar

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    onNavigateToLearn: () -> Unit,
    onNavigateToLabs: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToServices: () -> Unit,
    onNavigateToRoadmap: () -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onContinueLesson: (courseId: String, lessonId: String) -> Unit
) {
    val completedCount = userProfile.completedLessons.size
    val isBeforeLearning = completedCount == 0

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Command Center Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "$greeting, ${userProfile.name.ifBlank { "Engineer" }}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Ready to build in the cloud?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ElectricCyan,
                    fontFamily = FontFamily.Monospace
                )
            }

            // Streak indicator
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkNavyCardElevated,
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = "Streak", tint = StatusWarning, modifier = Modifier.size(16.dp))
                    Text("${userProfile.streakDays}d", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate100, fontFamily = FontFamily.Monospace)
                }
            }
        }

        if (isBeforeLearning) {
            // Before Learning Dashboard (Section 12)
            BeforeLearningHero(
                onStartFundamentals = onNavigateToLearn,
                onTryLabs = onNavigateToLabs,
                onBuildProjects = onNavigateToProjects,
                onExploreServices = onNavigateToServices
            )
        } else {
            // Active Learning Dashboard (Section 11)
            ActiveLearningHero(
                userProfile = userProfile,
                onContinueLesson = onContinueLesson
            )
        }

        // Quick Navigation Grid
        Text(
            text = "COMMAND CENTER QUICK LAUNCH",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Slate400,
            fontFamily = FontFamily.Monospace
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickLaunchCard(
                title = "Roadmap",
                subtitle = "22 Steps to Cloud Eng",
                icon = Icons.Default.Timeline,
                accentColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToRoadmap
            )
            QuickLaunchCard(
                title = "AI Cloud Tutor",
                subtitle = "Ask architecture & code",
                icon = Icons.Default.AutoAwesome,
                accentColor = CloudViolet,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToAiTutor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickLaunchCard(
                title = "Cloud Services",
                subtitle = "AWS / Azure / GCP Matrix",
                icon = Icons.Default.GridView,
                accentColor = CloudBlueLight,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToServices
            )
            QuickLaunchCard(
                title = "Interactive Labs",
                subtitle = "15 Simulators & Consoles",
                icon = Icons.Default.Terminal,
                accentColor = StatusHealthy,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToLabs
            )
        }

        // Philosophy & Brand Footer Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, CyberBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LEARN THE CLOUD. BUILD SYSTEMS.",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan,
                        fontFamily = FontFamily.Monospace
                    )
                    FreeBadge()
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Zero → Foundations → Cloud Services → Architecture → Security → DevOps → Projects → Certification → Cloud Engineer",
                    fontSize = 11.sp,
                    color = Slate300,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Created by Awiskar Acharya • 100% Free Cloud Academy",
                    fontSize = 10.sp,
                    color = Slate400,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
private fun BeforeLearningHero(
    onStartFundamentals: () -> Unit,
    onTryLabs: () -> Unit,
    onBuildProjects: () -> Unit,
    onExploreServices: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.5.dp, ElectricCyan.copy(alpha = 0.6f), RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "START YOUR CLOUD JOURNEY",
                    color = ElectricCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                FreeBadge()
            }

            Text(
                text = "Welcome to the Cloud Engineering Academy",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )

            Text(
                text = "No prior experience required. Begin from physical computer abstractions, navigate Linux terminals, build virtual private networks, deploy Kubernetes clusters, and prepare for industry cloud architecture certifications.",
                fontSize = 13.sp,
                color = Slate300,
                lineHeight = 18.sp
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onStartFundamentals,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Learn the Fundamentals", fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onTryLabs,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Try Labs", fontSize = 12.sp)
                    }
                    OutlinedButton(
                        onClick = onBuildProjects,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Build Projects", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ActiveLearningHero(
    userProfile: UserProfile,
    onContinueLesson: (courseId: String, lessonId: String) -> Unit
) {
    val currentCourse = CloudCoursesData.courses.find { it.id == userProfile.currentCourseId }
        ?: CloudCoursesData.courses.first()
    val currentLesson = currentCourse.lessons.find { it.id == userProfile.currentLessonId }
        ?: currentCourse.lessons.first()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.5.dp, CloudBlueLight, RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CONTINUE LEARNING",
                    color = ElectricCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                StatusBadge("In Progress")
            }

            Text(
                text = currentCourse.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )

            Text(
                text = "${currentLesson.title} • ${currentLesson.durationMinutes} min",
                fontSize = 13.sp,
                color = CloudBlueLight,
                fontWeight = FontWeight.SemiBold
            )

            // Progress bar
            val totalLessons = CloudCoursesData.courses.sumOf { it.lessons.size }
            val progress = (userProfile.completedLessons.size.toFloat() / totalLessons.coerceAtLeast(1))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = ElectricCyan,
                    trackColor = Slate800
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${userProfile.completedLessons.size} of $totalLessons lessons complete", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                    Text("${(progress * 100).toInt()}%", fontSize = 11.sp, color = ElectricCyan, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }

            Button(
                onClick = { onContinueLesson(currentCourse.id, currentLesson.id) },
                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("continue_lesson_button"),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Continue Lesson", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun QuickLaunchCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, CyberBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(20.dp))
            }
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate100)
            Text(subtitle, fontSize = 11.sp, color = Slate400, lineHeight = 14.sp)
        }
    }
}
