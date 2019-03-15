package bluenet.com.lab5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化頁面
        Log.e(TAG,"onCreate")
        //連結畫面
        setContentView(R.layout.activity_main)
        //連接Adapter，讓畫面(Fragment)與ViewPager建立關聯
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

    override fun onRestart() {
        super.onRestart()
        //返回頁面
        Log.e(TAG,"onRestart")
    }

    override fun onStart() {
        super.onStart()
        //頁面可見
        Log.e(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        //頁面與使用者開始互動
        Log.e(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        //離開頁面
        Log.e(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        //頁面不可見
        Log.e(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        //回收頁面
        Log.e(TAG,"onDestroy")
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    //回傳對應位置的Fragment，決定頁面的呈現順序
    override fun getItem(position: Int) = when(position){
        0 -> FirstFragment()    //第一頁要呈現的Fragment
        1 -> SecondFragment()   //第二頁要呈現的Fragment
        else -> ThirdFragment() //第三頁要呈現的Fragment
    }
    //回傳Fragment頁數
    override fun getCount() = 3
}