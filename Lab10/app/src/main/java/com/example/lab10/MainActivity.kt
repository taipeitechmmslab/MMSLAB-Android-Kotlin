package com.example.lab10

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // 將變數與XML元件綁定
        val imgFrame = findViewById<ImageView>(R.id.imgFrame)
        val imgTween = findViewById<ImageView>(R.id.imgTween)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnAlpha = findViewById<Button>(R.id.btnAlpha)
        val btnScale = findViewById<Button>(R.id.btnScale)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnRotate = findViewById<Button>(R.id.btnRotate)

        // 將圖片背景轉為AnimationDrawable
        val frameAnim = imgFrame.background as AnimationDrawable

        btnStart.setOnClickListener {
            frameAnim.start()
        }

        btnStop.setOnClickListener {
            frameAnim.stop()
        }

        btnAlpha.setOnClickListener {
            val anim = AlphaAnimation(
                1.0f, // 起始透明度
                0.2f // 結束透明度
            )
            anim.duration = 1000 // 動畫持續一秒
            imgTween.startAnimation(anim) // 執行動畫
        }

        btnScale.setOnClickListener {
            val anim = ScaleAnimation(
                1.0f, // X起始比例
                1.5f, // X結束比例
                1.0f, // Y起始比例
                1.5f // Y結束比例
            )
            anim.duration = 1000 // 動畫持續一秒
            imgTween.startAnimation(anim) // 執行動畫
        }

        btnTranslate.setOnClickListener {
            val anim = TranslateAnimation(
                0f, // X起點
                100f, // X終點
                0f, // Y起點
                -100f // Y終點
            )
            anim.duration = 1000 // 動畫持續一秒
            imgTween.startAnimation(anim) // 執行動畫
        }

        btnRotate.setOnClickListener {
            val anim = RotateAnimation(
                0f, // 起始角度
                360f, // 結束角度
                RotateAnimation.RELATIVE_TO_SELF, // X以自身位置旋轉
                0.5f, // X旋轉中心點
                RotateAnimation.RELATIVE_TO_SELF, // Y以自身位置旋轉
                0.5f // Y旋轉中心點
            )
            anim.duration = 1000 // 動畫持續一秒
            imgTween.startAnimation(anim) // 執行動畫
        }
    }
}