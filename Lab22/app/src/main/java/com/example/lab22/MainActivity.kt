package com.example.lab22

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lab22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 宣告 DataBinding 和 ViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInitialize()
    }

    // 執行初始化
    private fun doInitialize() {
        enableEdgeToEdge()
        // 設定 DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 取得 ViewModel 實例
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // 設定 DataBinding 生命週期
        binding.lifecycleOwner = this
        // 設定 DataBinding 的參數
        binding.vm = viewModel
    }
}