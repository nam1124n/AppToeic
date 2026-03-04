package com.example.toeic_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streak_table")
data class StreakEntity(
    @PrimaryKey
    val id: Int = 1, // Store a single row
    val currentStreak: Int = 0,
    val lastCompletedDate: String? = null // Stored as ISO-8601 string
)
