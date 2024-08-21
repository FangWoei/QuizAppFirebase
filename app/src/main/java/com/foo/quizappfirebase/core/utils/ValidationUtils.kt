package com.foo.quizappfirebase.core.utils

import com.foo.quizappfirebase.data.model.ValidationField

object ValidationUtils {
    fun validate(vararg fields: ValidationField): String? {
        fields.forEach { field ->
            if (!Regex(field.regExp).matches(field.value)) {
                return field.errMsg
            }
        }
        return null
    }
}