package com.example.lab20

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // 模擬網路是否有問題，true 代表有問題，false 代表沒問題
    private val networkErr = listOf(true, false, false)

    // 已註冊過的帳號列表
    private val accountList = mutableListOf<String>()

    // 註冊結果，第一個參數為是否成功，第二個參數為訊息
    private val _registerResult = MutableLiveData<Pair<Boolean, String>>()
    val registerResult: LiveData<Pair<Boolean, String>> = _registerResult

    // 註冊帳號
    fun registerAccount(
        account: String,
        password: String
    ) {
        when {
            // 帳號或密碼為空
            account.isEmpty() || password.isEmpty() ->
                _registerResult.value = Pair(false, "帳號或密碼不得為空")

            // 網路有問題
            networkErr.random() ->
                _registerResult.value = Pair(false, "網路錯誤")

            // 帳號已存在
            accountList.contains(account) ->
                _registerResult.value = Pair(false, "帳號已存在")

            // 註冊成功
            else -> {
                accountList.add(account)
                _registerResult.value = Pair(true, "註冊成功")
            }
        }
    }
}