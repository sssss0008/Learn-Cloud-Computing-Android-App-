package com.example.ui.screens.learn

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.Lesson
import com.example.data.model.QuizQuestion
import com.example.data.repository.CloudCoursesData
import com.example.ui.components.CodeSnippetCard
import com.example.ui.components.FreeBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun CourseListScreen(
    completedLessonIds: Set<String>,
    onSelectCourse: (courseId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Cloud Engineering Courses",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Complete 25-Course Curriculum • 100% Free",
                style = MaterialTheme.typography.bodySmall,
                color = ElectricCyan,
                fontFamily = FontFamily.Monospace
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(CloudCoursesData.courses) { course ->
                val completedInCourse = course.lessons.count { completedLessonIds.contains(it.id) }
                val isAllCompleted = completedInCourse == course.lessons.size && course.lessons.isNotEmpty()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectCourse(course.id) }
                        .border(
                            1.dp,
                            if (isAllCompleted) StatusHealthy.copy(alpha = 0.5f) else CyberBorder,
                            RoundedCornerShape(14.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isAllCompleted) StatusHealthy.copy(alpha = 0.2f)
                                    else CloudBlue.copy(alpha = 0.2f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${course.number}",
                                fontWeight = FontWeight.Bold,
                                color = if (isAllCompleted) StatusHealthy else ElectricCyan,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = course.level,
                                    fontSize = 10.sp,
                                    color = ElectricCyan,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$completedInCourse/${course.lessons.size} completed",
                                    fontSize = 11.sp,
                                    color = if (isAllCompleted) StatusHealthy else Slate400,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Text(
                                text = course.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Slate100
                            )

                            Text(
                                text = course.description,
                                fontSize = 12.sp,
                                color = Slate300,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    completedLessonIds: Set<String>,
    onSelectLesson: (lessonId: String) -> Unit,
    onBack: () -> Unit
) {
    val course = CloudCoursesData.courses.find { it.id == courseId } ?: CloudCoursesData.courses.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course.title, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "COURSE ${course.number} • ${course.level.uppercase()}",
                        color = ElectricCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    Text(
                        text = course.description,
                        fontSize = 13.sp,
                        color = Slate300,
                        lineHeight = 18.sp
                    )
                }
            }

            Text(
                text = "LESSONS IN THIS COURSE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate400,
                fontFamily = FontFamily.Monospace
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(course.lessons) { lesson ->
                    val isDone = completedLessonIds.contains(lesson.id)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectLesson(lesson.id) }
                            .border(
                                1.dp,
                                if (isDone) StatusHealthy.copy(alpha = 0.5f) else CyberBorder,
                                RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isDone) StatusHealthy else Slate800),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = DeepNavy, modifier = Modifier.size(16.dp))
                                } else {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(16.dp))
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(lesson.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate100)
                                Text("${lesson.durationMinutes} min • ${lesson.sections.size} sections", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
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
fun LessonScreen(
    courseId: String,
    lessonId: String,
    isCompleted: Boolean,
    onCompleteLesson: () -> Unit,
    onLaunchLab: ((labType: String) -> Unit)? = null,
    onBack: () -> Unit
) {
    val course = CloudCoursesData.courses.find { it.id == courseId } ?: CloudCoursesData.courses.first()
    val lesson = course.lessons.find { it.id == lessonId } ?: course.lessons.first()

    var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(lesson.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        Text("${course.title} • ${lesson.durationMinutes} mins", fontSize = 11.sp, color = ElectricCyan, fontFamily = FontFamily.Monospace)
                    }
                },
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
            // Summary Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "LEARNING OBJECTIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = lesson.summary,
                        fontSize = 14.sp,
                        color = Slate200,
                        lineHeight = 20.sp
                    )
                }
            }

            // Sections
            lesson.sections.forEach { section ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, CyberBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate100
                        )

                        Text(
                            text = section.content,
                            fontSize = 13.sp,
                            color = Slate300,
                            lineHeight = 20.sp
                        )

                        if (section.codeSnippet != null) {
                            CodeSnippetCard(
                                title = "TERMINAL / CODE EXAMPLE",
                                code = section.codeSnippet
                            )
                        }
                    }
                }
            }

            // Interactive Lab Callout (if available for this lesson)
            if (lesson.interactiveLabType != null && onLaunchLab != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    border = BorderStroke(1.5.dp, ElectricCyan),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("PRACTICE IN INTERACTIVE LAB", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            Text("Launch the hands-on simulator calibrated for this lesson.", fontSize = 12.sp, color = Slate200)
                        }
                        Button(
                            onClick = { onLaunchLab(lesson.interactiveLabType) },
                            colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Open Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Quick Knowledge Check (Quiz)
            if (lesson.quizQuestions.isNotEmpty()) {
                val q = lesson.quizQuestions.first()
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, CyberBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "KNOWLEDGE CHECK QUIZ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan,
                            fontFamily = FontFamily.Monospace
                        )

                        Text(
                            text = q.question,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate100
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            q.options.forEachIndexed { idx, opt ->
                                val isSelected = selectedQuizOption == idx
                                val isCorrect = idx == q.correctIndex
                                val borderCol = when {
                                    quizSubmitted && isCorrect -> StatusHealthy
                                    quizSubmitted && isSelected && !isCorrect -> StatusError
                                    isSelected -> ElectricCyan
                                    else -> CyberBorder
                                }

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            if (!quizSubmitted) selectedQuizOption = idx
                                        }
                                        .border(1.dp, borderCol, RoundedCornerShape(8.dp)),
                                    color = if (isSelected) CloudBlue.copy(alpha = 0.2f) else DarkNavyCard
                                ) {
                                    Text(
                                        text = opt,
                                        modifier = Modifier.padding(12.dp),
                                        fontSize = 13.sp,
                                        color = Slate200
                                    )
                                }
                            }
                        }

                        if (!quizSubmitted) {
                            Button(
                                onClick = { quizSubmitted = true },
                                enabled = selectedQuizOption != null,
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Check Answer")
                            }
                        } else {
                            Text(
                                text = "Explanation: ${q.explanation}",
                                fontSize = 12.sp,
                                color = if (selectedQuizOption == q.correctIndex) StatusHealthy else StatusWarning,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // Complete Lesson CTA Button
            Button(
                onClick = onCompleteLesson,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("complete_lesson_action"),
                colors = ButtonDefaults.buttonColors(containerColor = if (isCompleted) StatusHealthy else CloudBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isCompleted) "Completed (Tap to review again)" else "Mark Lesson as Completed",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
