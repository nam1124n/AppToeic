package com.example.toeic_app.domain.usecase.streak

import com.example.toeic_app.domain.model.streak.Streak
import com.example.toeic_app.domain.repository.streak.StreakRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class GetStreakUseCase @Inject constructor(
    private val repository: StreakRepository
) {
    operator fun invoke(): Flow<Streak> {
        return repository.getStreak().map { streak ->
            val today = LocalDate.now()
            if (streak.lastCompletedDate != null) {
                val daysBetween = ChronoUnit.DAYS.between(streak.lastCompletedDate, today)
                if (daysBetween > 1) {
                    // Missed a day, streak is broken, show as 0 in UI
                    return@map streak.copy(currentStreak = 0)
                }
            }
            streak
        }
    }
}
