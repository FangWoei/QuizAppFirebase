package com.foo.quizappfirebase.data.model

data class User(
    val username:String,
    val email: String,
    val role: Role
) {
    companion object {

        fun fromMap(map: Map<*,*>): User {
            return User(
                username = map["username"].toString(),
                email = map["email"].toString(),
                role = Role.valueOf(map["role"].toString())
            )
        }
    }
}