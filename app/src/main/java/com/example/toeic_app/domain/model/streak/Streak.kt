package com.example.toeic_app.domain.model.streak

import java.time.LocalDate

data class Streak(
    val currentStreak: Int,
    val lastCompletedDate: LocalDate?,
    val studyHistory: List<LocalDate>
)
