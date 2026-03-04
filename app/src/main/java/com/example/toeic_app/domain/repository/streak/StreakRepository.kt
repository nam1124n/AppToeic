package com.example.toeic_app.domain.repository.streak

import com.example.toeic_app.domain.model.streak.Streak
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StreakRepository {
    fun getStreak(): Flow<Streak>
    suspend fun updateStreak(streak: Int, lastCompletedDate: LocalDate)
    suspend fun addStudyDate(date: LocalDate)
}
