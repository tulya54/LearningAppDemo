package com.thirtyeight.thirtyeight.util

import android.content.Context

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
interface StringResourceMapper {

    fun getString(resId: Int): String
}

class DefaultStringResourceMapper(private val context: Context) : StringResourceMapper {

    override fun getString(resId: Int) =
            context.getString(resId)
}