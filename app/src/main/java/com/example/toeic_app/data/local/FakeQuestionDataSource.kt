package com.example.toeic_app.data.local

import com.example.toeic_app.domain.model.AnswerOption
import com.example.toeic_app.domain.model.PartType
import com.example.toeic_app.domain.model.Question
import com.example.toeic_app.domain.model.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeQuestionDataSource @Inject constructor() {

    private val _userAnswers = MutableStateFlow<Map<String, UserAnswer>>(emptyMap())
    val userAnswers: StateFlow<Map<String, UserAnswer>> = _userAnswers.asStateFlow()

    private val questions = listOf(
        Question(
            id = "q1",
            partType = PartType.PART_5,
            questionText = "The manager _______ the staff to complete the report by Friday.",
            options = listOf(
                AnswerOption("a", "wanted", "A"),
                AnswerOption("b", "asked", "B"),
                AnswerOption("c", "suggested", "C"),
                AnswerOption("d", "hoped", "D")
            ),
            correctAnswerId = "b" // asked
        ),
        Question(
            id = "q2",
            partType = PartType.PART_5,
            questionText = "Mr. Lee checks his email _______ he arrives at the office.",
            options = listOf(
                AnswerOption("a", "as soon as", "A"),
                AnswerOption("b", "during", "B"),
                AnswerOption("c", "until", "C"),
                AnswerOption("d", "despite", "D")
            ),
            correctAnswerId = "a" // as soon as
        ),
        Question(
            id = "q3",
            partType = PartType.PART_5,
            questionText = "Please sign the document and return it _______ the envelope provided.",
            options = listOf(
                AnswerOption("a", "on", "A"),
                AnswerOption("b", "to", "B"),
                AnswerOption("c", "at", "C"),
                AnswerOption("d", "in", "D")
            ),
            correctAnswerId = "d" // in
        )
        // Add more questions as needed
    )

    fun getQuestions(): List<Question> {
        return questions
    }

    fun saveUserAnswer(userAnswer: UserAnswer) {
        _userAnswers.update { currentMap ->
            currentMap + (userAnswer.questionId to userAnswer)
        }
    }

    fun getUserAnswer(questionId: String): UserAnswer? {
        return _userAnswers.value[questionId]
    }
}
