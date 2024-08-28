package com.foo.quizappfirebase.data.model

data class Quiz(
    val id: String? = null,
    val title: String,
    val desc: String,
    val quizIdForSearch: String,
    val timeLimit: Int,
    val questions: List<Question> = emptyList()
) {
    fun toMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "title" to title,
            "desc" to desc,
            "quizIdForSearch" to quizIdForSearch,
            "timeLimit" to timeLimit,
            "questions" to questions.map { it.toMap() }
        )

    companion object {
        fun fromMap(map: Map<*, *>): Quiz =
            Quiz(
                id = map["id"] as? String,
                title = map["title"] as String,
                desc = map["desc"] as String,
                quizIdForSearch = map["quizIdForSearch"] as String,
                timeLimit = (map["timeLimit"] as? Long)?.toInt() ?:0,
                questions = (map["questions"] as? List<Map<*, *>>)?.map { Question.fromMap(it) } ?: emptyList()
            )
    }
}