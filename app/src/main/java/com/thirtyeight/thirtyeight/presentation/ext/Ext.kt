package com.thirtyeight.thirtyeight.presentation.ext

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import com.thirtyeight.thirtyeight.presentation.logic.InputState
import com.thirtyeight.thirtyeight.presentation.ui.InputView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
fun Random.randomFloat(min: Float, max: Float) =
        min + Random.nextFloat() * (max - min)

@ColorInt
fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun TextView.setTextColorAttr(
        @AttrRes attrColor: Int
) {
    setTextColor(context.getColorFromAttr(attrColor))
}

fun Long.convertToPresentableString(format: SimpleDateFormat): String {
    return format.format(Date(this))
}

val <T> T.exhaustive: T
    get() = this

fun Context?.inflateLayout(layoutRes: Int, root: ViewGroup? = null, attachToRoot: Boolean = false) =
        LayoutInflater.from(this).inflate(layoutRes, root, attachToRoot)

fun Int?.orZero() = this ?: 0

fun Int.toDp(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                offer(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun InputState.bindToInputView(inputView: InputView) {
    inputView.text = text
    inputView.showError = showError
    if (errorRes != 0)
        inputView.errorText = inputView.context.getString(errorRes)
    else
        inputView.errorText = ""
    inputView.invalidateViewState()
}

fun Context.obtainStyledAttributeResource(attr: Int): Int {
    val textSizeAttr = intArrayOf(attr)
    val a = obtainStyledAttributes(textSizeAttr)
    val res = a.getResourceId(0, -1)
    a.recycle()
    return res
}