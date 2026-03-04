package com.example.toeic_app.data.repository.streak

import com.example.toeic_app.data.local.dao.StreakDao
import com.example.toeic_app.data.local.entity.StreakEntity
import com.example.toeic_app.data.local.entity.StudyHistoryEntity
import com.example.toeic_app.domain.model.streak.Streak
import com.example.toeic_app.domain.repository.streak.StreakRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject

class StreakRepositoryImpl @Inject constructor(
    private val dao: StreakDao
) : StreakRepository {

    override fun getStreak(): Flow<Streak> {
        return combine(
            dao.getStreakFlow(),
            dao.getStudyHistoryFlow()
        ) { streakEntity, historyEntities ->
            val history = historyEntities.mapNotNull {
                try { LocalDate.parse(it.dateString) } catch (e: DateTimeParseException) { null }
            }
            
            if (streakEntity == null) {
                Streak(currentStreak = 0, lastCompletedDate = null, studyHistory = history)
            } else {
                val lastDate = try { 
                    streakEntity.lastCompletedDate?.let { LocalDate.parse(it) } 
                } catch (e: DateTimeParseException) { null }
                
                Streak(
                    currentStreak = streakEntity.currentStreak,
                    lastCompletedDate = lastDate,
                    studyHistory = history
                )
            }
        }
    }

    override suspend fun updateStreak(streak: Int, lastCompletedDate: LocalDate) {
        val entity = StreakEntity(
            id = 1,
            currentStreak = streak,
            lastCompletedDate = lastCompletedDate.toString()
        )
        dao.updateStreak(entity)
    }

    override suspend fun addStudyDate(date: LocalDate) {
        val entity = StudyHistoryEntity(dateString = date.toString())
        dao.insertStudyDate(entity)
    }
}
