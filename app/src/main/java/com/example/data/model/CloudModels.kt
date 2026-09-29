package com.example.data.model

data class UserProfile(
    val name: String = "",
    val isOnboarded: Boolean = false,
    val learningGoals: List<String> = emptyList(),
    val experienceLevel: String = "",
    val cloudInterests: List<String> = emptyList(),
    val completedLessons: Set<String> = emptySet(),
    val completedLabs: Set<String> = emptySet(),
    val completedProjects: Set<String> = emptySet(),
    val quizScores: Map<String, Int> = emptyMap(),
    val bookmarkedIds: Set<String> = emptySet(),
    val userNotes: Map<String, String> = emptyMap(),
    val streakDays: Int = 1,
    val lastActiveDate: String = "",
    val currentCourseId: String = "course-1",
    val currentLessonId: String = "c1-l1",
    val themeMode: ThemeMode = ThemeMode.DARK
)

enum class ThemeMode {
    DARK, LIGHT, AMOLED, HIGH_CONTRAST
}

data class Course(
    val id: String,
    val number: Int,
    val title: String,
    val description: String,
    val level: String, // Level 1 - Level 10
    val iconName: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val courseId: String,
    val title: String,
    val durationMinutes: Int,
    val summary: String,
    val sections: List<LessonSection>,
    val interactiveLabType: String? = null,
    val quizQuestions: List<QuizQuestion> = emptyList()
)

data class LessonSection(
    val title: String,
    val content: String,
    val codeSnippet: String? = null,
    val visualType: String? = null // e.g. "vm-diagram", "dns-flow", "vpc-topology", "docker-layers"
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

enum class LabCategory {
    NETWORKING, LINUX, COMPUTE, STORAGE, DATABASE, CONTAINERS, KUBERNETES, SECURITY, DEVOPS, IAC, OBSERVABILITY, ARCHITECTURE, COST
}

data class CloudLab(
    val id: String,
    val title: String,
    val category: LabCategory,
    val difficulty: String, // Beginner, Intermediate, Advanced, Expert Challenge
    val durationMin: Int,
    val skills: List<String>,
    val description: String,
    val labRoute: String
)

data class CloudService(
    val id: String,
    val name: String,
    val category: String, // Compute, Storage, Database, Networking, Security, DevOps, Monitoring, AI/ML, Messaging
    val description: String,
    val whenToUse: String,
    val architectureExample: String,
    val relatedServices: List<String>,
    val providerA: String, // e.g. AWS equivalent
    val providerB: String, // e.g. Azure equivalent
    val providerC: String  // e.g. GCP equivalent
)

data class CloudProject(
    val id: String,
    val title: String,
    val difficulty: String,
    val timeEstimate: String,
    val objective: String,
    val concepts: List<String>,
    val architectureSummary: String,
    val requirements: List<String>,
    val steps: List<String>,
    val verificationPoints: List<String>
)

data class GlossaryTerm(
    val id: String,
    val term: String,
    val category: String,
    val simpleExplanation: String,
    val technicalExplanation: String,
    val example: String,
    val relatedConcepts: List<String>
)

data class CheatSheetEntry(
    val id: String,
    val category: String, // Linux, Git, Docker, Kubernetes, Networking, Bash, Cloud CLI, YAML, JSON
    val command: String,
    val purpose: String,
    val example: String,
    val explanation: String
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val requiredType: String,
    val requiredCount: Int
)

data class MockExamQuestion(
    val id: String,
    val domain: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
