package com.foo.quizappfirebase.core.services

import android.content.Context
import com.foo.quizappfirebase.data.model.Role
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthService(
    private val context: Context
){
    private val auth = FirebaseAuth.getInstance()
    suspend fun createUserWithEmailAndPassword(email: String, password: String):Boolean {
        val res = auth.createUserWithEmailAndPassword(
            email,password
        ).await()
        return res.user != null
    }

    suspend fun loginEmailWithPassword(email: String,password: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(email,password).await()
        return res.user
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun logOut() { auth.signOut() }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun getUid(): String? {
        return auth.currentUser?.uid
    }

}