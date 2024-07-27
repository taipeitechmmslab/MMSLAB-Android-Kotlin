package com.example.lab5

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SecondFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("SecondFragment","onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SecondFragment","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("SecondFragment","onCreateView")
        // 填充 Layout 佈局，返回 View 對象
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("SecondFragment","onViewCreated")
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