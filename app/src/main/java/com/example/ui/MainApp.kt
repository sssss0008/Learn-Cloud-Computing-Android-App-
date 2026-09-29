package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ThemeMode
import com.example.data.preferences.UserStorage
import com.example.ui.components.FreeBadge
import com.example.ui.screens.aitutor.AiTutorScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.labs.*
import com.example.ui.screens.learn.CourseDetailScreen
import com.example.ui.screens.learn.CourseListScreen
import com.example.ui.screens.learn.LessonScreen
import com.example.ui.screens.onboarding.OnboardingFlow
import com.example.ui.screens.practice.MockExamScreen
import com.example.ui.screens.practice.PracticeCenterScreen
import com.example.ui.screens.profile.*
import com.example.ui.screens.projects.ProjectDetailScreen
import com.example.ui.screens.projects.ProjectsHubScreen
import com.example.ui.screens.reference.CheatSheetScreen
import com.example.ui.screens.reference.GlossaryScreen
import com.example.ui.screens.roadmap.RoadmapScreen
import com.example.ui.screens.services.CloudServicesScreen
import com.example.ui.theme.*

enum class AppTab(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    LEARN("Learn", Icons.Default.School),
    LABS("Labs", Icons.Default.Terminal),
    PROJECTS("Projects", Icons.Default.AccountTree),
    PROFILE("Profile", Icons.Default.Person)
}

sealed class SubScreen {
    object None : SubScreen()
    data class CourseDetail(val courseId: String) : SubScreen()
    data class Lesson(val courseId: String, val lessonId: String) : SubScreen()
    data class Lab(val route: String, val labId: String) : SubScreen()
    data class ProjectDetail(val projectId: String) : SubScreen()
    object Roadmap : SubScreen()
    object Services : SubScreen()
    object Practice : SubScreen()
    object MockExam : SubScreen()
    object AiTutor : SubScreen()
    object Glossary : SubScreen()
    object CheatSheet : SubScreen()
    object SkillMap : SubScreen()
    object Achievements : SubScreen()
    object Certificate : SubScreen()
    object Creator : SubScreen()
    object Settings : SubScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    userStorage: UserStorage = UserStorage(LocalContext.current)
) {
    val userProfile by userStorage.userProfile.collectAsState()

    var currentTab by remember { mutableStateOf(AppTab.HOME) }
    var currentSubScreen by remember { mutableStateOf<SubScreen>(SubScreen.None) }

    LearnCloudTheme(themeMode = userProfile.themeMode) {
        if (!userProfile.isOnboarded) {
            OnboardingFlow(
                onFinishOnboarding = { name, goals, experience, interests ->
                    userStorage.completeOnboarding(name, goals, experience, interests)
                }
            )
        } else {
            // Main Scaffold with custom Top Bar and M3 Bottom Navigation
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars),
                topBar = {
                    if (currentSubScreen == SubScreen.None) {
                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            tonalElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Learn Cloud Computing",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = "By Awiskar Acharya",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = ElectricCyan,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = { currentSubScreen = SubScreen.CheatSheet },
                                        modifier = Modifier.size(38.dp)
                                    ) {
                                        Icon(Icons.Default.Terminal, contentDescription = "Cheat Sheet", tint = Slate300, modifier = Modifier.size(20.dp))
                                    }
                                    IconButton(
                                        onClick = { currentSubScreen = SubScreen.Glossary },
                                        modifier = Modifier.size(38.dp)
                                    ) {
                                        Icon(Icons.Default.MenuBook, contentDescription = "Glossary", tint = Slate300, modifier = Modifier.size(20.dp))
                                    }
                                    IconButton(
                                        onClick = { currentSubScreen = SubScreen.AiTutor },
                                        modifier = Modifier.size(38.dp)
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, contentDescription = "AI Tutor", tint = ElectricCyan, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }
                },
                bottomBar = {
                    if (currentSubScreen == SubScreen.None) {
                        NavigationBar(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .border(BorderStroke(1.dp, CyberBorder)),
                            containerColor = DarkNavyCard,
                            tonalElevation = 6.dp
                        ) {
                            AppTab.entries.forEach { tab ->
                                val selected = currentTab == tab
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { currentTab = tab },
                                    icon = {
                                        Icon(
                                            tab.icon,
                                            contentDescription = tab.title,
                                            tint = if (selected) ElectricCyan else Slate400
                                        )
                                    },
                                    label = {
                                        Text(
                                            tab.title,
                                            fontSize = 11.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selected) ElectricCyan else Slate400
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = DarkNavyCardElevated
                                    )
                                )
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    when (val screen = currentSubScreen) {
                        SubScreen.None -> {
                            when (currentTab) {
                                AppTab.HOME -> HomeScreen(
                                    userProfile = userProfile,
                                    onNavigateToLearn = { currentTab = AppTab.LEARN },
                                    onNavigateToLabs = { currentTab = AppTab.LABS },
                                    onNavigateToProjects = { currentTab = AppTab.PROJECTS },
                                    onNavigateToServices = { currentSubScreen = SubScreen.Services },
                                    onNavigateToRoadmap = { currentSubScreen = SubScreen.Roadmap },
                                    onNavigateToAiTutor = { currentSubScreen = SubScreen.AiTutor },
                                    onContinueLesson = { cId, lId ->
                                        currentSubScreen = SubScreen.Lesson(cId, lId)
                                    }
                                )
                                AppTab.LEARN -> CourseListScreen(
                                    completedLessonIds = userProfile.completedLessons,
                                    onSelectCourse = { courseId ->
                                        currentSubScreen = SubScreen.CourseDetail(courseId)
                                    }
                                )
                                AppTab.LABS -> LabsHubScreen(
                                    completedLabIds = userProfile.completedLabs,
                                    onOpenLab = { route, labId ->
                                        currentSubScreen = SubScreen.Lab(route, labId)
                                    }
                                )
                                AppTab.PROJECTS -> ProjectsHubScreen(
                                    completedProjectIds = userProfile.completedProjects,
                                    onSelectProject = { projectId ->
                                        currentSubScreen = SubScreen.ProjectDetail(projectId)
                                    }
                                )
                                AppTab.PROFILE -> ProfileScreen(
                                    userProfile = userProfile,
                                    onOpenSkillMap = { currentSubScreen = SubScreen.SkillMap },
                                    onOpenAchievements = { currentSubScreen = SubScreen.Achievements },
                                    onOpenCertificate = { currentSubScreen = SubScreen.Certificate },
                                    onOpenCreator = { currentSubScreen = SubScreen.Creator },
                                    onOpenSettings = { currentSubScreen = SubScreen.Settings }
                                )
                            }
                        }
                        is SubScreen.CourseDetail -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            CourseDetailScreen(
                                courseId = screen.courseId,
                                completedLessonIds = userProfile.completedLessons,
                                onSelectLesson = { lessonId ->
                                    userStorage.setCurrentLesson(screen.courseId, lessonId)
                                    currentSubScreen = SubScreen.Lesson(screen.courseId, lessonId)
                                },
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        is SubScreen.Lesson -> {
                            BackHandler { currentSubScreen = SubScreen.CourseDetail(screen.courseId) }
                            LessonScreen(
                                courseId = screen.courseId,
                                lessonId = screen.lessonId,
                                isCompleted = userProfile.completedLessons.contains(screen.lessonId),
                                onCompleteLesson = {
                                    userStorage.markLessonCompleted(screen.lessonId)
                                },
                                onLaunchLab = { labType ->
                                    val labRoute = when (labType) {
                                        "LINUX_TERMINAL" -> "terminal_lab"
                                        "SUBNET_CALC" -> "subnet_calculator"
                                        "VM_PROVISIONING" -> "vm_provisioning"
                                        "VPC_BUILDER" -> "vpc_builder"
                                        "DNS_VISUALIZER" -> "dns_visualizer"
                                        "IAM_POLICY" -> "iam_policy"
                                        "DOCKER_CONTAINER" -> "docker_lab"
                                        "KUBERNETES" -> "kubernetes_lab"
                                        "API_EXPLORER" -> "api_explorer"
                                        "CICD_PIPELINE" -> "cicd_pipeline"
                                        "IAC_PLAYGROUND" -> "iac_playground"
                                        "MONITORING_DASHBOARD" -> "monitoring_dashboard"
                                        "INCIDENT_SIMULATOR" -> "incident_simulator"
                                        "COST_CALCULATOR" -> "cost_calculator"
                                        "ARCHITECTURE_DESIGNER" -> "architecture_designer"
                                        else -> "terminal_lab"
                                    }
                                    currentSubScreen = SubScreen.Lab(labRoute, "lab-$labRoute")
                                },
                                onBack = { currentSubScreen = SubScreen.CourseDetail(screen.courseId) }
                            )
                        }
                        is SubScreen.Lab -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            when (screen.route) {
                                "terminal_lab" -> TerminalLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "subnet_calculator" -> SubnetCalculatorScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "network_visualizer" -> NetworkVisualizerScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "vm_provisioning" -> VmProvisioningLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "vpc_builder" -> VpcBuilderScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "dns_visualizer" -> DnsVisualizerScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "iam_policy" -> IamPolicyLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "docker_lab" -> DockerLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "kubernetes_lab" -> KubernetesLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "api_explorer" -> ApiExplorerScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "cicd_pipeline" -> CicdPipelineScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "iac_playground" -> IacPlaygroundScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "monitoring_dashboard" -> MonitoringLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "incident_simulator" -> IncidentSimulatorScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "architecture_designer" -> ArchitectureDesignerScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                "cost_calculator" -> CostLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                                else -> TerminalLabScreen(
                                    onBack = { currentSubScreen = SubScreen.None },
                                    onCompleteLab = { userStorage.markLabCompleted(screen.labId) }
                                )
                            }
                        }
                        is SubScreen.ProjectDetail -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            ProjectDetailScreen(
                                projectId = screen.projectId,
                                isCompleted = userProfile.completedProjects.contains(screen.projectId),
                                onCompleteProject = { userStorage.markProjectCompleted(screen.projectId) },
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Roadmap -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            RoadmapScreen(
                                completedLessonCount = userProfile.completedLessons.size,
                                onSelectStep = { /* Inspect step */ }
                            )
                        }
                        SubScreen.Services -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            CloudServicesScreen()
                        }
                        SubScreen.Practice -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            PracticeCenterScreen(
                                onStartMockExam = { currentSubScreen = SubScreen.MockExam }
                            )
                        }
                        SubScreen.MockExam -> {
                            BackHandler { currentSubScreen = SubScreen.Practice }
                            MockExamScreen(
                                onBack = { currentSubScreen = SubScreen.Practice }
                            )
                        }
                        SubScreen.AiTutor -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            AiTutorScreen(
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Glossary -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            GlossaryScreen(
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.CheatSheet -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            CheatSheetScreen(
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.SkillMap -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            SkillMapScreen(
                                userProfile = userProfile,
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Achievements -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            AchievementsScreen(
                                userProfile = userProfile,
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Certificate -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            CertificateScreen(
                                userProfile = userProfile,
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Creator -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            CreatorScreen(
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                        SubScreen.Settings -> {
                            BackHandler { currentSubScreen = SubScreen.None }
                            SettingsScreen(
                                currentTheme = userProfile.themeMode,
                                onSelectTheme = { userStorage.setThemeMode(it) },
                                onResetProgress = { userStorage.resetProgress() },
                                onBack = { currentSubScreen = SubScreen.None }
                            )
                        }
                    }
                }
            }
        }
    }
}
