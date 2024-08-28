package com.foo.quizappfirebase.data.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: String,
) {
    fun toMap(): Map<String, Any?> =
        mapOf(
            "text" to text,
            "options" to options,
            "correctAnswer" to correctAnswer,
        )

    companion object {
        fun fromMap(map: Map<*, *>): Question =
            Question(
                text = map["text"] as String,
                options = (map["options"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                correctAnswer = map["correctAnswer"] as String
            )
    }
}
