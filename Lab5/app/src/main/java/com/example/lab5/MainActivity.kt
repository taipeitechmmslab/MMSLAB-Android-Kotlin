package com.example.lab5

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.e("MainActivity","onCreate")
        // 取得 ViewPager2 元件
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        // 建立 ViewPagerAdapter 並設定給 ViewPager2
        val adapter = ViewPagerAdapter(supportFragmentManager, this.lifecycle)
        viewPager2.adapter = adapter
        // 預先載入鄰近的頁面
        viewPager2.offscreenPageLimit = 1
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity","onDestroy")
    }
}