package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.ThemeMode
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class UserStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("learn_cloud_prefs", Context.MODE_PRIVATE)

    private val _userProfile = MutableStateFlow(loadProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private fun loadProfile(): UserProfile {
        val name = prefs.getString("user_name", "") ?: ""
        val isOnboarded = prefs.getBoolean("is_onboarded", false)
        val goals = prefs.getStringSet("learning_goals", emptySet())?.toList() ?: emptyList()
        val experience = prefs.getString("experience_level", "") ?: ""
        val interests = prefs.getStringSet("cloud_interests", emptySet())?.toList() ?: emptyList()
        val completedLessons = prefs.getStringSet("completed_lessons", emptySet()) ?: emptySet()
        val completedLabs = prefs.getStringSet("completed_labs", emptySet()) ?: emptySet()
        val completedProjects = prefs.getStringSet("completed_projects", emptySet()) ?: emptySet()
        val bookmarked = prefs.getStringSet("bookmarked_ids", emptySet()) ?: emptySet()
        val streak = prefs.getInt("streak_days", 1)
        val lastDate = prefs.getString("last_active_date", "") ?: ""
        val currentCourse = prefs.getString("current_course", "course-1") ?: "course-1"
        val currentLesson = prefs.getString("current_lesson", "c1-l1") ?: "c1-l1"
        val themeStr = prefs.getString("theme_mode", ThemeMode.DARK.name) ?: ThemeMode.DARK.name
        val themeMode = runCatching { ThemeMode.valueOf(themeStr) }.getOrDefault(ThemeMode.DARK)

        return UserProfile(
            name = name,
            isOnboarded = isOnboarded,
            learningGoals = goals,
            experienceLevel = experience,
            cloudInterests = interests,
            completedLessons = completedLessons,
            completedLabs = completedLabs,
            completedProjects = completedProjects,
            bookmarkedIds = bookmarked,
            streakDays = streak,
            lastActiveDate = lastDate,
            currentCourseId = currentCourse,
            currentLessonId = currentLesson,
            themeMode = themeMode
        )
    }

    fun completeOnboarding(
        name: String,
        goals: List<String>,
        experience: String,
        interests: List<String>
    ) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        prefs.edit()
            .putString("user_name", name)
            .putBoolean("is_onboarded", true)
            .putStringSet("learning_goals", goals.toSet())
            .putString("experience_level", experience)
            .putStringSet("cloud_interests", interests.toSet())
            .putString("last_active_date", today)
            .apply()

        _userProfile.value = loadProfile()
    }

    fun markLessonCompleted(lessonId: String) {
        val current = prefs.getStringSet("completed_lessons", emptySet()) ?: emptySet()
        val updated = current + lessonId
        prefs.edit().putStringSet("completed_lessons", updated).apply()
        updateStreak()
        _userProfile.value = loadProfile()
    }

    fun markLabCompleted(labId: String) {
        val current = prefs.getStringSet("completed_labs", emptySet()) ?: emptySet()
        val updated = current + labId
        prefs.edit().putStringSet("completed_labs", updated).apply()
        updateStreak()
        _userProfile.value = loadProfile()
    }

    fun markProjectCompleted(projectId: String) {
        val current = prefs.getStringSet("completed_projects", emptySet()) ?: emptySet()
        val updated = current + projectId
        prefs.edit().putStringSet("completed_projects", updated).apply()
        updateStreak()
        _userProfile.value = loadProfile()
    }

    fun toggleBookmark(id: String) {
        val current = prefs.getStringSet("bookmarked_ids", emptySet()) ?: emptySet()
        val updated = if (current.contains(id)) current - id else current + id
        prefs.edit().putStringSet("bookmarked_ids", updated).apply()
        _userProfile.value = loadProfile()
    }

    fun setCurrentLesson(courseId: String, lessonId: String) {
        prefs.edit()
            .putString("current_course", courseId)
            .putString("current_lesson", lessonId)
            .apply()
        _userProfile.value = loadProfile()
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString("theme_mode", mode.name).apply()
        _userProfile.value = loadProfile()
    }

    private fun updateStreak() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val lastDate = prefs.getString("last_active_date", "") ?: ""
        if (lastDate != today) {
            val streak = prefs.getInt("streak_days", 1) + 1
            prefs.edit()
                .putString("last_active_date", today)
                .putInt("streak_days", streak)
                .apply()
        }
    }

    fun resetProgress() {
        prefs.edit()
            .remove("completed_lessons")
            .remove("completed_labs")
            .remove("completed_projects")
            .remove("bookmarked_ids")
            .putInt("streak_days", 1)
            .apply()
        _userProfile.value = loadProfile()
    }
}
