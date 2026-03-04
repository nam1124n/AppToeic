package com.example.toeic_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_history_table")
data class StudyHistoryEntity(
    @PrimaryKey
    val dateString: String // ISO-8601 string e.g. 2023-10-25
)
