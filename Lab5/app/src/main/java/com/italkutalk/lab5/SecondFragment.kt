package com.italkutalk.lab5
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
class SecondFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SecondFragment","onCreate")
    }
    //在 onCreateView 中定義 SecondFragment 的畫面為 fragment_second
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("SecondFragment","onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("SecondFragment","onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.e("SecondFragment","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("SecondFragment","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("SecondFragment","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("SecondFragment","onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("SecondFragment","onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("SecondFragment","onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.e("SecondFragment","onDetach")
    }
}