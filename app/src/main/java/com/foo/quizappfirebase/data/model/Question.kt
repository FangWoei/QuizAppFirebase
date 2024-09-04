package com.foo.quizappfirebase.data.model

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
) {

    companion object {
        fun fromMap(map: Map<*,*>): Question {
            return map.let {
                Question(
                    questionText = it["questionText"].toString(),
                    options = (it["options"] as? List<*>)?.map { option -> option.toString() } ?: emptyList(),
                    correctAnswer = it["correctAnswer"].toString()
                )
            }
        }
    }
}
