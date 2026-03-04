package com.example.toeic_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.toeic_app.data.local.entity.StreakEntity
import com.example.toeic_app.data.local.entity.StudyHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_table WHERE id = 1")
    fun getStreakFlow(): Flow<StreakEntity?>

    @Query("SELECT * FROM streak_table WHERE id = 1")
    suspend fun getStreakSync(): StreakEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStreak(streak: StreakEntity)

    @Query("SELECT * FROM study_history_table ORDER BY dateString ASC")
    fun getStudyHistoryFlow(): Flow<List<StudyHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudyDate(studyDate: StudyHistoryEntity)
}
