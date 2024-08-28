package com.foo.quizappfirebase.core.utils

import android.view.View
import com.foo.quizappfirebase.data.model.ValidationField

object ValidationUtils {
    fun View.setVisibility(condition: Boolean) {
        this.visibility = if(condition) View.GONE else View.VISIBLE
    }

    fun validate(vararg fields: ValidationField): String? {
        fields.forEach { field ->
            if (!Regex(field.regExp).matches(field.value)) {
                return field.errMsg
            }
        }
        return null
    }
}