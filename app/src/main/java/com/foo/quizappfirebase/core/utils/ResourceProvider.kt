package com.foo.quizappfirebase.core.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider(
    private val context: Context
) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)

    fun getFormattedString(@StringRes resId: Int, args: String): String =
        context.getString(resId, args)
}


