package com.example.lab21

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // 設定公式的 LiveData
    private val _formula = MutableLiveData<String>()
    val formula: LiveData<String> = _formula

    // 設定結果的 LiveData
    private val _result = MutableLiveData<Float>()
    val result: LiveData<Float> = _result

    // 紀錄最後一次按下的鍵
    private var lastKey = ""

    // 宣告運算符號類型
    private val operators = listOf("+", "-", "×", "÷")

    fun onKeyClick(key: String) {
        when (key) {
            // 清除公式和結果
            "C" -> {
                _formula.value = ""
                _result.value = 0f
            }

            // 處理運算符
            in operators -> {
                val currentFormula = _formula.value ?: ""
                if (currentFormula.isEmpty() || lastKey in operators) {
                    // 如果公式是空的或最後一次按下的是運算符，則不處理
                    return
                } else {
                    // 確保運算符和操作數之間有空格
                    _formula.value = "$currentFormula $key "
                }
            }

            // 處理數字和小數點
            else -> {
                val currentFormula = _formula.value ?: ""
                if (key == "." && (currentFormula.isEmpty() || lastKey in operators)) {
                    // 小數點不能作為第一個字符或直接跟在運算符後面
                    return
                }
                _formula.value = "$currentFormula$key"
                // 計算結果
                _result.value = calculate()
            }
        }
        lastKey = key
    }

    private fun calculate(): Float {
        return try {
            val formula = _formula.value ?: return 0f
            val parts = formula.split(" ")

            // 處理乘除運算
            val stack = mutableListOf<String>()
            var i = 0
            while (i < parts.size) {
                val part = parts[i]
                if (part == "×" || part == "÷") {
                    val prev = stack.removeAt(stack.size - 1).toFloat()
                    val next = parts[i + 1].toFloat()
                    val result = if (part == "×") prev * next else prev / next
                    stack.add(result.toString())
                    i += 2
                } else {
                    stack.add(part)
                    i++
                }
            }

            // 處理加減運算
            var result = stack[0].toFloat()
            i = 1
            while (i < stack.size) {
                val operator = stack[i]
                val next = stack[i + 1].toFloat()
                result = when (operator) {
                    "+" -> result + next
                    "-" -> result - next
                    else -> result
                }
                i += 2
            }

            result
        } catch (e: NumberFormatException) {
            -1f
        }
    }

    // 初始化預設值
    init {
        _formula.value = ""
        _result.value = 0f
    }
}