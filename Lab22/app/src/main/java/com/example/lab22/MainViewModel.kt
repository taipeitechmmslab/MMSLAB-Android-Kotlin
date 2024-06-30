package com.example.lab22

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // 靜態成員
    companion object {
        // 預設時間
        private const val DEFAULT_TIME = 60
        // 預設進度
        private const val DEFAULT_PROGRESS = 100
        // 預設倍數
        private const val DEFAULT_MULTIPLIER = 1
    }

    // 剩餘時間
    private val _timeLeft = MutableStateFlow(DEFAULT_TIME)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    // 進度條
    private val _progress = MutableStateFlow(DEFAULT_PROGRESS)
    val progress: StateFlow<Int> = _progress.asStateFlow()

    // 是否正在倒數
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    // 倍數
    private val _multiplier = MutableStateFlow(DEFAULT_MULTIPLIER)
    val multiplier: StateFlow<Int> = _multiplier.asStateFlow()

    // 格式化後的時間
    private val _formattedTimeLeft = MutableStateFlow(formatTime(DEFAULT_TIME))
    val formattedTimeLeft: StateFlow<String> = _formattedTimeLeft.asStateFlow()

    // 倒數計時的工作物件
    private var job: Job? = null

    // 最大時間
    private var maxTime = DEFAULT_TIME

    // 開始或暫停倒數計時
    fun startOrPauseTimer() {
        if (job == null) {
            // 開始倒數計時
            _isRunning.value = true
            // 使用協程執行倒數計時
            job = viewModelScope.launch {
                while (_timeLeft.value > 0) {
                    // 根據倍數延遲一段時間
                    delay(1000 / _multiplier.value.toLong())
                    // 更新剩餘時間
                    _timeLeft.value -= 1
                    // 更新進度條
                    updateProgress()
                }
                // 倒數計時結束，job 設為 null
                job = null
                // 更新狀態
                _isRunning.value = false
            }
        } else {
            // 暫停倒數計時
            job?.cancel()
            job = null
            _isRunning.value = false
        }
    }

    // 加五秒
    fun addFiveSeconds() {
        _timeLeft.value += 5
        // 更新最大時間
        if (_timeLeft.value > maxTime) {
            maxTime = _timeLeft.value
        }
        // 更新進度條
        updateProgress()
    }

    // 重設倒數計時
    fun resetTimer() {
        _timeLeft.value = DEFAULT_TIME
        maxTime = DEFAULT_TIME
        _progress.value = DEFAULT_PROGRESS
        _isRunning.value = false
        job?.cancel()
        job = null
        _formattedTimeLeft.value = formatTime(DEFAULT_TIME)
    }

    // 加倍數
    fun addMultiplier() {
        // 1 -> 2 -> 4 -> 8 -> 1
        _multiplier.value = when (_multiplier.value) {
            1 -> 2
            2 -> 4
            4 -> 8
            else -> 1
        }
    }

    // 更新進度條
    private fun updateProgress() {
        // 計算進度，最大為 100
        _progress.value = (_timeLeft.value.toDouble() / maxTime * 100).toInt()
        _formattedTimeLeft.value = formatTime(_timeLeft.value)
    }

    // 格式化時間
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        // 釋放資源
        job?.cancel()
        job = null
    }
}
