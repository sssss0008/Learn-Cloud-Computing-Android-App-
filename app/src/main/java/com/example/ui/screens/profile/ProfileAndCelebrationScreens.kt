package com.example.ui.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ThemeMode
import com.example.data.model.UserProfile
import com.example.data.repository.CloudCoursesData
import com.example.data.repository.CloudLabsData
import com.example.data.repository.CloudProjectsData
import com.example.data.repository.CloudReferenceData
import com.example.ui.components.FreeBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onOpenSkillMap: () -> Unit,
    onOpenAchievements: () -> Unit,
    onOpenCertificate: () -> Unit,
    onOpenCreator: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val totalLessons = CloudCoursesData.courses.sumOf { it.lessons.size }
    val totalLabs = CloudLabsData.labs.size
    val totalProjects = CloudProjectsData.projects.size

    val lessonPercent = if (totalLessons > 0) (userProfile.completedLessons.size.toFloat() / totalLessons * 100).toInt() else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User identity hero
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, CyberBorder)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(CloudBlue, ElectricCyan))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userProfile.name.take(1).ifBlank { "C" }.uppercase(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = DarkNavyCard,
                        border = BorderStroke(1.dp, CyberBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = StatusWarning, modifier = Modifier.size(16.dp))
                            Text("${userProfile.streakDays} Day Streak", fontSize = 11.sp, color = Slate100, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Text(
                    text = userProfile.name.ifBlank { "Cloud Engineer" },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate100
                )

                Text(
                    text = "Track: ${userProfile.experienceLevel.ifBlank { "All Levels" }} • ${userProfile.learningGoals.joinToString(", ").ifBlank { "Cloud Architecture" }}",
                    fontSize = 12.sp,
                    color = Slate300
                )
            }
        }

        // Progress Metric Cards
        Text(
            text = "PROGRESSION OVERVIEW",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Slate400,
            fontFamily = FontFamily.Monospace
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProgressStatCard("LESSONS", "${userProfile.completedLessons.size}/$totalLessons", "$lessonPercent%", ElectricCyan, Modifier.weight(1f))
            ProgressStatCard("LABS", "${userProfile.completedLabs.size}/$totalLabs", "${(userProfile.completedLabs.size.toFloat() / totalLabs * 100).toInt()}%", StatusHealthy, Modifier.weight(1f))
            ProgressStatCard("PROJECTS", "${userProfile.completedProjects.size}/$totalProjects", "${(userProfile.completedProjects.size.toFloat() / totalProjects * 100).toInt()}%", CloudViolet, Modifier.weight(1f))
        }

        // Navigation Items
        ProfileMenuRow("Cloud Skill Map", "Visual prerequisite topology graph", Icons.Default.AccountTree, onOpenSkillMap)
        ProfileMenuRow("Achievements & Badges", "${CloudReferenceData.achievements.size} badges to unlock", Icons.Default.EmojiEvents, onOpenAchievements)
        ProfileMenuRow("Certificate of Completion", "Official course credential", Icons.Default.CardMembership, onOpenCertificate)
        ProfileMenuRow("About Awiskar Acharya", "Creator & Free Education Mission", Icons.Default.Person, onOpenCreator)
        ProfileMenuRow("Settings & Theme Mode", "Dark, Light, AMOLED, Contrast", Icons.Default.Settings, onOpenSettings)
    }
}

@Composable
private fun ProgressStatCard(label: String, value: String, percent: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Slate100, fontFamily = FontFamily.Monospace)
            Text(percent, fontSize = 11.sp, color = color, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun ProfileMenuRow(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, CyberBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkNavyCardElevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate100)
                Text(subtitle, fontSize = 11.sp, color = Slate400)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Slate500)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillMapScreen(
    userProfile: UserProfile,
    onBack: () -> Unit
) {
    val skills = listOf(
        "Linux CLI" to (userProfile.completedLabs.contains("lab-linux-terminal")),
        "Networking & Subnets" to (userProfile.completedLabs.contains("lab-subnet-calculator")),
        "Cloud Fundamentals" to (userProfile.completedLessons.size >= 4),
        "Virtualization & VMs" to (userProfile.completedLabs.contains("lab-vm-provisioning")),
        "VPC Architecture" to (userProfile.completedLabs.contains("lab-vpc-builder")),
        "Security & IAM" to (userProfile.completedLabs.contains("lab-iam-policy")),
        "Docker Containers" to (userProfile.completedLabs.contains("lab-docker-container")),
        "Kubernetes" to (userProfile.completedLabs.contains("lab-kubernetes")),
        "CI/CD Automation" to (userProfile.completedLabs.contains("lab-cicd-pipeline")),
        "Infrastructure as Code" to (userProfile.completedLabs.contains("lab-iac-playground")),
        "Monitoring & SRE" to (userProfile.completedLabs.contains("lab-monitoring-dashboard")),
        "Cloud Architecture" to (userProfile.completedProjects.isNotEmpty())
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Engineering Skill Map", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(skills) { (skill, isMastered) ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isMastered) StatusHealthy else CyberBorder)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isMastered) StatusHealthy else Slate800),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isMastered) Icons.Default.Check else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isMastered) DeepNavy else Slate400,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(skill, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 14.sp)
                        }
                        StatusBadge(if (isMastered) "Active" else "Offline")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    userProfile: UserProfile,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements & Badges", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(CloudReferenceData.achievements) { ach ->
                val isUnlocked = when (ach.requiredType) {
                    "LESSONS" -> userProfile.completedLessons.size >= ach.requiredCount
                    "LABS" -> userProfile.completedLabs.size >= ach.requiredCount
                    "PROJECTS" -> userProfile.completedProjects.size >= ach.requiredCount
                    else -> userProfile.completedLessons.isNotEmpty()
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = if (isUnlocked) DarkNavyCardElevated else DarkNavyCard),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isUnlocked) ElectricCyan else CyberBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) ElectricCyan.copy(alpha = 0.2f) else Slate800),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = if (isUnlocked) ElectricCyan else Slate500,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(ach.title, fontWeight = FontWeight.Bold, color = if (isUnlocked) Slate100 else Slate400, fontSize = 14.sp)
                            Text(ach.description, fontSize = 12.sp, color = Slate400)
                        }

                        if (isUnlocked) {
                            StatusBadge("Passed")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificateScreen(
    userProfile: UserProfile,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val today = remember { SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(Date()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Completion Certificate", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Elegant Vector Certificate Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, ElectricCyan, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(CloudBlue, ElectricCyan))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CardMembership, contentDescription = null, tint = DeepNavy, modifier = Modifier.size(32.dp))
                    }

                    Text(
                        text = "CERTIFICATE OF COMPLETION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "Learn Cloud Computing",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "This credential certifies that",
                        fontSize = 12.sp,
                        color = Slate400
                    )

                    Text(
                        text = userProfile.name.ifBlank { "Cloud Engineer" },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "has successfully completed the comprehensive curriculum covering Cloud Fundamentals, Linux Administration, Networking & VPC, Docker, Kubernetes Orchestration, CI/CD, Infrastructure as Code, Observability, and Distributed Cloud Architecture.",
                        fontSize = 12.sp,
                        color = Slate300,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Created by:", fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                            Text("Awiskar Acharya", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Slate100)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Date Issued:", fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                            Text(today, fontSize = 11.sp, color = Slate200, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }

            // LinkedIn connection callout
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("WANT TO CONNECT?", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text(
                        text = "Congratulations on completing your cloud computing journey. If you'd like to connect with the creator, you can message Awiskar Acharya on LinkedIn.",
                        fontSize = 13.sp,
                        color = Slate200,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A66C2)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Message Awiskar Acharya on LinkedIn", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Awiskar Acharya", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("CREATOR & ARCHITECT", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("Awiskar Acharya", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Slate100)
                    Text("Creator of Learn Cloud Computing", fontSize = 13.sp, color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                    HorizontalDivider(color = CyberBorder)
                    Text(
                        text = "Awiskar Acharya is committed to empowering students, software engineers, and IT professionals worldwide with practical, high-grade cloud engineering knowledge without financial barriers.",
                        fontSize = 13.sp,
                        color = Slate300,
                        lineHeight = 20.sp
                    )
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("100% FREE EDUCATION PHILOSOPHY", color = StatusHealthy, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text(
                        text = "• Zero Subscriptions\n• Zero Paywalls or Locked Lessons\n• Zero Credit Systems\n• Everything inside the learning experience is 100% free.",
                        fontSize = 13.sp,
                        color = Slate200,
                        lineHeight = 20.sp
                    )
                }
            }

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A66C2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Connect with Awiskar Acharya on LinkedIn", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onSelectTheme: (ThemeMode) -> Unit,
    onResetProgress: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Visual Themes", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("THEME MODE", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeMode.entries.forEach { mode ->
                    val isSelected = currentTheme == mode
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectTheme(mode) },
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) DarkNavyCardElevated else DarkNavyCard),
                        border = BorderStroke(1.dp, if (isSelected) ElectricCyan else CyberBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onSelectTheme(mode) },
                                colors = RadioButtonDefaults.colors(selectedColor = ElectricCyan)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(mode.name, fontWeight = FontWeight.Bold, color = Slate100)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("DATA PERSISTENCE", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

            OutlinedButton(
                onClick = onResetProgress,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusError),
                border = BorderStroke(1.dp, StatusError),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset Learning Progress")
            }
        }
    }
}
