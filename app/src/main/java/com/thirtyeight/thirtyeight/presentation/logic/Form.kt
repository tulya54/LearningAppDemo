package com.thirtyeight.thirtyeight.presentation.logic

/**
 * Created by nikolozakhvlediani on 4/17/21.
 */
data class Form(
        val inputs: List<InputState>
) {

    fun updateInputText(id: Long, text: String): Form {
        val mappedInputs = inputs.map {
            if (it.id == id)
                it.copy(text = text)
            else
                it.copy()
        }
        return copy(inputs = mappedInputs)
    }

    fun clearErrors(): Form {
        val mappedInputs = inputs.map {
            it.copy(showError = false, errorRes = 0)
        }
        return copy(inputs = mappedInputs)
    }

    fun showError(id: Long, textRes: Int): Form {
        val mappedInputs = inputs.map {
            if (it.id == id)
                it.copy(showError = true, errorRes = textRes)
            else
                it.copy()
        }
        return copy(inputs = mappedInputs)
    }

    fun showErrorOnFullForm(textRes: Int): Form {
        val mappedInputs = inputs.mapIndexed { index, input ->
            if (index == inputs.size - 1)
                input.copy(showError = true, errorRes = textRes)
            else
                input.copy(showError = true)
        }
        return copy(inputs = mappedInputs)
    }

    fun getInputById(id: Long): InputState {
        return inputs.first { it.id == id }
    }
}