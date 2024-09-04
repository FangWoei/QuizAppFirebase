package com.foo.quizappfirebase.data.model


data class Quiz(
    val id: String? = null,
    val teacherId: String? = null,
    val title: String,
    val desc: String,
    val timeLimit: String,
    val questions: List<Question> = emptyList(),
    val studentList: List<Student> = emptyList()
) {

    fun toMap(): Map<String, Any?> {
        return hashMapOf(
            "title" to title,
            "desc" to desc,
            "teacherId" to teacherId,
            "timeLimit" to timeLimit,
            "questions" to questions,
            "studentList" to studentList
        )
    }



    companion object {
        fun fromMap(map: Map<String, Any?>): Quiz {
            val questionsList = (map["questions"] as? List<Map<String, Any?>>)?.map { Question.fromMap(it) } ?: emptyList()
            return Quiz(
                title = map["title"].toString(),
                desc = map["desc"].toString(),
                teacherId = map["teacherId"].toString(),
                timeLimit = map["timeLimit"].toString(),
                questions = (map["questions"] as? ArrayList<*>)?.let { questions ->
                    questions.map { Question.fromMap(it as Map<*, *>) }
                } ?: emptyList(),
                studentList = (map["studentList"] as? ArrayList<*>)?.let { students ->
                    students.map { Student.fromMap(it as Map<*, *>) }
                } ?: emptyList()
                )
        }
    }
}