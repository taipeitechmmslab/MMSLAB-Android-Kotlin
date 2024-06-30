package com.example.lab21

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab21.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 宣告 DataBinding 和 ViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInitialize()
        setupKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解除 DataBinding
        binding.unbind()
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

    // 設定鍵盤
    private fun setupKeyboard() {
        // 設定鍵盤
        val keys = listOf(
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "×",
            "C", "0", ".", "÷"
        )
        // 設定 Adapter
        val adapter = KeyboardAdapter(keys) {
            // 當按下鍵盤時，呼叫 ViewModel 的 onKeyClick 方法
            viewModel.onKeyClick(it)
        }
        // 設定 LayoutManager
        val layoutManager = GridLayoutManager(this, 4)
        // 設定 RecyclerView
        binding.rvKeyboard.layoutManager = layoutManager
        binding.rvKeyboard.adapter = adapter
    }
}