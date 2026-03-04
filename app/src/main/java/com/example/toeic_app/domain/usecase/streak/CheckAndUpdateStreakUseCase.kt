package com.example.toeic_app.domain.usecase.streak

import com.example.toeic_app.domain.repository.streak.StreakRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CheckAndUpdateStreakUseCase @Inject constructor(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(durationInMinutes: Long, testCompleted: Boolean) {
        if (!testCompleted && durationInMinutes < 10) {
            return // Conditions not met
        }

        val today = LocalDate.now()
        val currentData = repository.getStreak().first()
        
        // Add to history if not already there
        if (!currentData.studyHistory.contains(today)) {
            repository.addStudyDate(today)
        }

        val lastCompleted = currentData.lastCompletedDate
        
        if (lastCompleted == today) {
            // Already completed today, do nothing to streak count
            return
        }

        var newStreak = currentData.currentStreak
        if (lastCompleted != null) {
            val daysBetween = ChronoUnit.DAYS.between(lastCompleted, today)
            if (daysBetween == 1L) {
                newStreak += 1
            } else if (daysBetween > 1L) {
                newStreak = 1
            }
        } else {
            // First time completing ever
            newStreak = 1
        }
        
        repository.updateStreak(newStreak, today)
    }
}
