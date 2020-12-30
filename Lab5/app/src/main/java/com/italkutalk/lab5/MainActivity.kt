package com.italkutalk.lab5
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("MainActivity","onCreate")
        //建立 FragmentPagerAdapter 物件
        val adapter = ViewPagerAdapter(supportFragmentManager)
        //連接 Adapter，讓畫面(Fragment)與 ViewPager 建立關聯
        findViewById<ViewPager>(R.id.viewPager).adapter = adapter
    }
    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity","onRestart")
    }
    override fun onStart() {
        super.onStart()
        Log.e("MainActivity","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("MainActivity","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("MainActivity","onPause")
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

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    //回傳對應位置的 Fragment，決定頁面的呈現順序
    override fun getItem(position: Int) = when(position) {
        0 -> FirstFragment() //第一頁要呈現的 Fragment
        1 -> SecondFragment() //第二頁要呈現的 Fragment
        else -> ThirdFragment() //第三頁要呈現的 Fragment
    }
    //回傳 Fragment 頁數
    override fun getCount() = 3
}