package bluenet.com.lab5

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ThirdFragment : Fragment() {
    companion object {
        const val TAG = "ThirdFragment"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化頁面
        Log.e(TAG,"onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //連結畫面
        Log.e(TAG,"onCreateView")
        return inflater.inflate(R.layout.fragment_thrid, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //連結Fragment與Activity
        Log.e(TAG,"onActivityCreated")
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

    override fun onDestroyView() {
        super.onDestroyView()
        //移除畫面
        Log.e(TAG,"onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        //回收頁面
        Log.e(TAG,"onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        //移除Fragment
        Log.e(TAG,"onDetach")
    }
}