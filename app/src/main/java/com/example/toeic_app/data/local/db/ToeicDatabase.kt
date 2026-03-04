package com.example.toeic_app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.toeic_app.data.local.dao.StreakDao
import com.example.toeic_app.data.local.entity.StreakEntity
import com.example.toeic_app.data.local.entity.StudyHistoryEntity

@Database(
    entities = [StreakEntity::class, StudyHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToeicDatabase : RoomDatabase() {
    abstract val streakDao: StreakDao
    
    companion object {
        const val DATABASE_NAME = "toeic_db"
    }
}
