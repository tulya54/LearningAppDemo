package com.thirtyeight.thirtyeight.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.nikoloz14.myextensions.makeVisibleOrGone
import com.nikoloz14.myextensions.setReadOnly
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ext.setTextColorAttr
import com.thirtyeight.thirtyeight.util.SimpleTextWatcher

/**
 * Created by nikolozakhvlediani on 4/14/21.
 */
@SuppressLint("ClickableViewAccessibility")
class InputView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var viewState = ViewState(
            text = "",
            label = "",
            hint = "",
            showError = false,
            errorText = "",
            state = State.INACTIVE,
            readOnly = false,
            inputType = EditorInfo.TYPE_CLASS_TEXT
    )

    var previousViewState: ViewState = viewState

    var text: String
        set(value) {
            viewState = viewState.copy(text = value)
        }
        get() = viewState.text

    var label: String
        set(value) {
            viewState = viewState.copy(label = value)
        }
        get() = viewState.label

    var hint: String
        set(value) {
            viewState = viewState.copy(hint = value)
        }
        get() = viewState.hint

    var showError: Boolean
        set(value) {
            viewState = viewState.copy(showError = value)
            viewState = viewState.copy(state = getStateByViewState(editText.isFocused))
        }
        get() = viewState.showError

    var errorText: String
        set(value) {
            viewState = viewState.copy(errorText = value)
        }
        get() = viewState.errorText

    var state: State
        set(value) {
            viewState = viewState.copy(state = value)
        }
        get() = viewState.state

    var readOnly: Boolean
        set(value) {
            viewState = viewState.copy(readOnly = value)
        }
        get() = viewState.readOnly

    var inputType: Int
        set(value) {
            viewState = viewState.copy(inputType = value)
        }
        get() = viewState.inputType

    var onDrawableEndClicked: (() -> Unit)? = null

    private val labelTextView: TextView
    val editText: EditText
    private val errorTextView: TextView
    private val dummyView: View

    init {
        val view = context.inflateLayout(R.layout.view_input, this, true)
        labelTextView = view.findViewById(R.id.label_text_view)
        editText = view.findViewById(R.id.edit_text)
        errorTextView = view.findViewById(R.id.error_text_view)
        dummyView = view.findViewById(R.id.dummy_view)
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.InputView, 0, 0).apply {
                text = getString(R.styleable.InputView_android_text) ?: ""
                label = getString(R.styleable.InputView_label) ?: ""
                hint = getString(R.styleable.InputView_android_hint) ?: ""
                inputType = getInt(R.styleable.InputView_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
                readOnly = getBoolean(R.styleable.InputView_readOnly, false)
                setDrawableEnd(getResourceId(R.styleable.InputView_android_drawableEnd, 0))
                recycle()
            }
        }
        editText.setOnFocusChangeListener { _, hasFocus ->
            viewState = viewState.copy(state = getStateByViewState(hasFocus))
            invalidateViewState()
        }
        editText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                text = s.toString()
            }
        })
        editText.setOnTouchListener(OnTouchListener { _, event ->
            val dEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[dEnd] != null) {
                    if (event.rawX >= editText.right - editText.compoundDrawables[dEnd].bounds.width()) {
                        onDrawableEndClicked?.invoke()
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
        invalidateViewState()
    }

    fun invalidateViewState() {
        with(viewState) {
            if (viewState.text != editText.text.toString())
                editText.setText(text)
            editText.hint = hint
            labelTextView.text = label
            errorTextView.makeVisibleOrGone(showError && errorText.isNotEmpty())
            errorTextView.text = errorText
            if (viewState.readOnly != previousViewState.readOnly)
                makeReadOnly(viewState.readOnly)
            if (viewState.inputType != previousViewState.inputType)
                editText.inputType = viewState.inputType
            previousViewState = viewState
            updateUiByState()
        }
    }

    private fun updateUiByState() {
        when (viewState.state) {
            State.INACTIVE -> {
                editText.setBackgroundResource(R.drawable.background_input_inactive)
                editText.setTextColorAttr(R.attr.colorInputTextActive)
            }
            State.ACTIVE -> {
                editText.setBackgroundResource(R.drawable.background_input_active)
                editText.setTextColorAttr(R.attr.colorInputTextActive)
            }
            State.FILLED -> {
                editText.setBackgroundResource(R.drawable.background_input_filled)
                editText.setTextColorAttr(R.attr.colorInputTextFilled)
            }
            State.ERROR -> {
                editText.setBackgroundResource(R.drawable.background_input_error)
                editText.setTextColorAttr(R.attr.colorInputTextError)
            }
        }
    }

    private fun getStateByViewState(isFocused: Boolean): State {
        return if (viewState.showError) {
            State.ERROR
        } else if (isFocused) {
            State.ACTIVE
        } else {
            if (text.isNotEmpty()) {
                State.FILLED
            } else {
                State.INACTIVE
            }
        }
    }

    var previousInputType = editText.inputType

    fun makeReadOnly(readOnly: Boolean) {
        if (readOnly && editText.inputType != InputType.TYPE_NULL) {
            previousInputType = editText.inputType
        }
        editText.setReadOnly(
                readOnly,
                if (!readOnly) previousInputType else InputType.TYPE_NULL
        )
        dummyView.makeVisibleOrGone(readOnly)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        dummyView.setOnClickListener(l)
    }

    fun setDrawableEnd(drawable: Int) {
        if (drawable != 0) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
        }
    }

    data class ViewState(
            val text: String,
            val label: String,
            val hint: String,
            val showError: Boolean,
            val errorText: String,
            val state: State,
            val readOnly: Boolean,
            val inputType: Int
    )

    enum class State {
        INACTIVE, ACTIVE, FILLED, ERROR
    }
}