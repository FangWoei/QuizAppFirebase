package com.foo.quizappfirebase.data.repo

import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepo(
    private val authService: AuthService
) {
    private fun getCollection(): CollectionReference {
        val uid = authService.getUid() ?: throw Exception("User doesn't exist!")
        return Firebase.firestore.collection("root_db/$uid/quizzes")
    }

    fun getAllQuiz() = callbackFlow<List<Quiz>> {
        val listener = getCollection().addSnapshotListener { value, error ->
            if(error != null) throw error
            val quizzes = mutableListOf<Quiz>()
            value?.documents?.map { snapshot ->
                snapshot.data?.let {
                    quizzes.add(Quiz.fromMap(it).copy(id = snapshot.id))
                }
            }
            trySend(quizzes)
        }
        awaitClose { listener.remove() }
    }

    suspend fun createQuiz(quiz: Quiz): String? {
        val res = getCollection().add(quiz.toMap()).await()
        return res?.id
    }

    suspend fun deleteQuiz(id: String) { getCollection().document(id).delete().await() }

    suspend fun updateQuiz(quiz: Quiz) {
        getCollection().document(quiz.id!!).set(quiz.toMap()).await()
    }

    suspend fun getQuizById(id: String): Quiz? {
        val res = getCollection().document(id).get().await()
        return res.data?.let { Quiz.fromMap(it).copy(id = res.id) }
    }

}