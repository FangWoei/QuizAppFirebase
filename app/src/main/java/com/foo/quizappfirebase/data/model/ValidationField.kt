package com.foo.quizappfirebase.data.model

data class ValidationField(
    val value: String,
    val regExp: String,
    val errMsg: String
)
