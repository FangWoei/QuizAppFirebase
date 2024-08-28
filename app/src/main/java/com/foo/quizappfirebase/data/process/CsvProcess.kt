package com.foo.quizappfirebase.data.process

import android.content.Context
import android.net.Uri
import com.foo.quizappfirebase.data.model.Question
import java.io.BufferedReader
import java.io.InputStreamReader


class CsvProcessor(private val context: Context) {

    fun processQuizCsv(uri: Uri): List<Question> {
        val questions = mutableListOf<Question>()

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.readLine()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    line?.split(",")?.let { fields ->
                        if (fields.size == 6) {
                            val question = Question(
                                text = fields[0].trim(),
                                options = fields.subList(1, 5).map { it.trim() },
                                correctAnswer = fields[5].trim()
                            )
                            questions.add(question)
                        }
                    }
                }
            }
        }

        return questions
    }
}
