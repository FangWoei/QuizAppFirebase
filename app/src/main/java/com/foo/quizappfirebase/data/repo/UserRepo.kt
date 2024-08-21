package com.foo.quizappfirebase.data.repo

import android.util.Log
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.model.Role
import com.foo.quizappfirebase.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserRepo (
    private val authService: AuthService
) {
    private fun getUid(): String {
        return authService.getUid() ?: throw Exception("User doesn't exist")
    }
    private fun getCollection(): CollectionReference {
        return Firebase.firestore
            .collection("users")
    }
    suspend fun createUser(user: User) {
        Log.d("debugging","user")
        getCollection().document(getUid()).set(user).await()
    }

    suspend fun getUser(): User? {
        val res = getCollection().document(getUid()).get().await()
        return res.data?.let { User.fromMap(it) }
    }



    suspend fun updateUser(user: User) {
        getCollection().document(getUid()).set(user).await()
    }
}