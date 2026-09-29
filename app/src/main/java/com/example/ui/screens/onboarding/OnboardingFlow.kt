package com.example.ui.screens.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun OnboardingFlow(
    onFinishOnboarding: (name: String, goals: List<String>, experience: String, interests: List<String>) -> Unit
) {
    var step by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    val selectedGoals = remember { mutableStateListOf<String>() }
    var selectedExperience by remember { mutableStateOf("Complete Beginner") }
    val selectedInterests = remember { mutableStateListOf("Cloud Infrastructure", "Linux", "Docker", "Security") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (step) {
                0 -> WelcomeStep(
                    onStart = { step = 1 }
                )
                1 -> NameStep(
                    name = name,
                    onNameChange = { name = it },
                    onNext = { if (name.isNotBlank()) step = 2 }
                )
                2 -> GoalsStep(
                    selectedGoals = selectedGoals,
                    onToggleGoal = { goal ->
                        if (selectedGoals.contains(goal)) selectedGoals.remove(goal) else selectedGoals.add(goal)
                    },
                    onNext = { step = 3 },
                    onBack = { step = 1 }
                )
                3 -> ExperienceStep(
                    selectedExperience = selectedExperience,
                    onSelectExperience = { selectedExperience = it },
                    onNext = { step = 4 },
                    onBack = { step = 2 }
                )
                4 -> InterestsStep(
                    selectedInterests = selectedInterests,
                    onToggleInterest = { item ->
                        if (selectedInterests.contains(item)) selectedInterests.remove(item) else selectedInterests.add(item)
                    },
                    onNext = { step = 5 },
                    onBack = { step = 3 }
                )
                5 -> PersonalizedStartStep(
                    name = name.ifBlank { "Cloud Engineer" },
                    experience = selectedExperience,
                    onStartLearning = {
                        onFinishOnboarding(
                            name.ifBlank { "Learner" },
                            selectedGoals.toList(),
                            selectedExperience,
                            selectedInterests.toList()
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun WelcomeStep(onStart: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "cloudFlow")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))

            FreeBadge()

            Spacer(modifier = Modifier.height(20.dp))

            // Hero visualizer: Cloud Infrastructure Ecosystem Canvas
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0xFF0284C7).copy(alpha = 0.25f),
                                Color(0xFF0F172A).copy(alpha = 0.9f)
                            )
                        )
                    )
                    .border(1.5.dp, ElectricCyan.copy(alpha = pulseAlpha), RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Background network grid dots
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    // Draw simulated connection lines
                    drawLine(
                        color = Color(0xFF00E5FF).copy(alpha = 0.4f),
                        start = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.3f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFF38BDF8).copy(alpha = 0.4f),
                        start = androidx.compose.ui.geometry.Offset(w * 0.8f, h * 0.3f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFF818CF8).copy(alpha = 0.4f),
                        start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.3f, h * 0.8f),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFF10B981).copy(alpha = 0.4f),
                        start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f),
                        end = androidx.compose.ui.geometry.Offset(w * 0.7f, h * 0.8f),
                        strokeWidth = 2f
                    )
                }

                // Central Cloud Node
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(CloudBlue, ElectricCyan)))
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudQueue,
                            contentDescription = "Cloud Core",
                            tint = DeepNavy,
                            modifier = Modifier.size(42.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "CLOUD MESH",
                        color = ElectricCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Orbiting nodes
                Box(modifier = Modifier.fillMaxSize().padding(14.dp)) {
                    MiniNode(Icons.Default.Storage, "DB", Alignment.TopStart)
                    MiniNode(Icons.Default.Dns, "DNS", Alignment.TopEnd)
                    MiniNode(Icons.Default.Terminal, "CLI", Alignment.BottomStart)
                    MiniNode(Icons.Default.Security, "IAM", Alignment.BottomEnd)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Learn Cloud Computing",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Created by Awiskar Acharya",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = ElectricCyan,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Understand how modern applications run in the cloud.",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start from zero and learn cloud infrastructure, networking, security, DevOps, containers, databases, architecture, and deployment.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("start_learning_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CloudBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Start Learning",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "100% Free • No Paywalls • Complete Academy",
                fontSize = 12.sp,
                color = Slate400,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun BoxScope.MiniNode(icon: ImageVector, label: String, align: Alignment) {
    Surface(
        modifier = Modifier
            .align(align)
            .clip(RoundedCornerShape(8.dp)),
        color = DarkNavyCardElevated,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(12.dp))
            Text(label, fontSize = 9.sp, color = Slate200, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun NameStep(
    name: String,
    onNameChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Step 1 of 4",
                color = ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "What should we call you?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We'll personalize your cloud learning journey, projects, and completion certificate.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Enter your name") },
                placeholder = { Text("e.g. Alex Rivera") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("name_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CloudBlueLight,
                    unfocusedBorderColor = CyberBorder
                ),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = ElectricCyan)
                }
            )
        }

        Button(
            onClick = onNext,
            enabled = name.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("name_continue_button"),
            colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
private fun GoalsStep(
    selectedGoals: List<String>,
    onToggleGoal: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val goals = listOf(
        "Cloud Engineer" to "Learn infrastructure and cloud platforms.",
        "DevOps" to "Learn deployment, automation, CI/CD, containers, and infrastructure.",
        "Software Developer" to "Learn how applications run in the cloud.",
        "System Administrator" to "Learn servers, networking, storage, and cloud infrastructure.",
        "Cybersecurity" to "Learn cloud security and identity.",
        "Data & AI" to "Learn cloud data platforms and AI infrastructure.",
        "College / Study" to "Learn cloud computing academically.",
        "Career" to "Build practical cloud engineering skills."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Step 2 of 4",
                color = ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "What do you want to learn cloud computing for?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Select all that apply to customize your path.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                goals.forEach { (title, desc) ->
                    val isSelected = selectedGoals.contains(title)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onToggleGoal(title) }
                            .border(
                                1.5.dp,
                                if (isSelected) ElectricCyan else CyberBorder,
                                RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) DarkNavyCardElevated else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (isSelected) ElectricCyan else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = desc,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { onToggleGoal(title) },
                                colors = CheckboxDefaults.colors(checkedColor = ElectricCyan)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back")
            }
            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(2f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ExperienceStep(
    selectedExperience: String,
    onSelectExperience: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val levels = listOf(
        "Complete Beginner" to "I am new to cloud computing.",
        "Beginner" to "I understand basic computers and programming.",
        "Intermediate" to "I understand servers and networking.",
        "Developer" to "I already build applications.",
        "IT Professional" to "I have infrastructure experience.",
        "Experienced" to "I want advanced cloud architecture."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Step 3 of 4",
                color = ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Where should we start?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "We will calibrate the lab complexity and foundational explanations.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                levels.forEach { (level, desc) ->
                    val isSelected = selectedExperience == level
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectExperience(level) }
                            .border(
                                1.5.dp,
                                if (isSelected) CloudBlueLight else CyberBorder,
                                RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) DarkNavyCardElevated else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onSelectExperience(level) },
                                colors = RadioButtonDefaults.colors(selectedColor = CloudBlueLight)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = level,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (isSelected) CloudBlueLight else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = desc,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back")
            }
            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(2f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InterestsStep(
    selectedInterests: List<String>,
    onToggleInterest: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val interests = listOf(
        "Cloud Infrastructure", "AWS Concepts", "Azure Concepts", "Google Cloud Concepts",
        "Networking", "Linux", "Docker", "Kubernetes", "DevOps", "CI/CD",
        "Security", "Databases", "Serverless", "Storage", "Monitoring",
        "Infrastructure as Code", "Cloud Architecture", "AI/ML Infrastructure"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Step 4 of 4",
                color = ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Cloud Interest Selection",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pick the topics you are most excited to build with.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(interests) { interest ->
                    val isSelected = selectedInterests.contains(interest)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onToggleInterest(interest) }
                            .border(
                                1.dp,
                                if (isSelected) ElectricCyan else CyberBorder,
                                RoundedCornerShape(10.dp)
                            ),
                        color = if (isSelected) DarkNavyCardElevated else MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) ElectricCyan else Slate600)
                            )
                            Text(
                                text = interest,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) ElectricCyan else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back")
            }
            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(2f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("See My Path", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun PersonalizedStartStep(
    name: String,
    experience: String,
    onStartLearning: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(CloudBlue, ElectricCyan))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = null,
                    tint = DeepNavy,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome, $name!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your cloud engineering journey starts here.",
                style = MaterialTheme.typography.bodyLarge,
                color = ElectricCyan,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RECOMMENDED STARTING PATH",
                            color = Slate400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        FreeBadge()
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Cloud Computing Fundamentals",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Calibrated for $experience level. Begins with hardware abstractions, OSI networking, Linux command line, and virtual machines before diving into container orchestration and multi-tier cloud architectures.",
                        fontSize = 13.sp,
                        color = Slate300,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Slate800
                        ) {
                            Text(
                                "25 Courses",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = Slate200,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Slate800
                        ) {
                            Text(
                                "15 Interactive Labs",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = ElectricCyan,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onStartLearning,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("launch_learning_button"),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Start Learning", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
