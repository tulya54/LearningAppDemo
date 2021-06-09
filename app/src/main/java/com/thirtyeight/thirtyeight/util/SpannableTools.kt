package com.thirtyeight.thirtyeight.util

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.thirtyeight.thirtyeight.R

object SpannableTools {

    private const val TAG = "SpannableTools"

    fun getSpannable(context: Context, firstText: String, secondText: String,
                     @ColorRes color: Int): SpannableString {
        val startIndex = 0
        val endIndex = firstText.length
        val spannableString = SpannableString(firstText + secondText)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)),
            startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    fun getSpannable(context: Context, text: String, @ColorRes color: Int): SpannableString {
        val startIndex = 0
        val endIndex = text.length
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
            startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}