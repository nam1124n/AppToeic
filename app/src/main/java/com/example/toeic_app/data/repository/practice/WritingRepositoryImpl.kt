package com.example.toeic_app.data.repository.practice

import android.content.Context
import com.example.toeic_app.data.dto.practice.WritingQuestionDto
import com.example.toeic_app.data.dto.practice.toDomain
import com.example.toeic_app.data.dto.practice.toDto
import com.example.toeic_app.domain.model.practice.WritingQuestion
import com.example.toeic_app.domain.repository.practice.WritingRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await
import java.io.InputStreamReader

class WritingRepositoryImpl(
    private val context: Context,
    private val firestore: FirebaseFirestore
) : WritingRepository {

    private val collection = firestore.collection("writing_questions")

    override suspend fun getWritingQuestion(testId: Int, questionNumber: Int): WritingQuestion? {
        return try {
            val snapshot = collection
                .whereEqualTo("testId", testId)
                .whereEqualTo("questionNumber", questionNumber)
                .get()
                .await()
            if (!snapshot.isEmpty) {
                snapshot.documents[0].toObject(WritingQuestionDto::class.java)?.toDomain()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllQuestions(): List<WritingQuestion> {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { it.toObject(WritingQuestionDto::class.java)?.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun insertQuestion(question: WritingQuestion): Result<Unit> {
        return try {
            val dto = question.toDto()
            collection.document(dto.id.toString()).set(dto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQuestion(question: WritingQuestion): Result<Unit> {
        return try {
            val dto = question.toDto()
            collection.document(dto.id.toString()).set(dto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteQuestion(id: Int): Result<Unit> {
        return try {
            collection.document(id.toString()).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun migrateData(): Result<Unit> {
        return try {
            val inputStream = context.assets.open("writing_questions.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<WritingQuestionDto>>() {}.type
            val questions: List<WritingQuestionDto> = Gson().fromJson(reader, type)
            reader.close()
            
            // Push all to Firestore
            for (q in questions) {
                collection.document(q.id.toString()).set(q).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
