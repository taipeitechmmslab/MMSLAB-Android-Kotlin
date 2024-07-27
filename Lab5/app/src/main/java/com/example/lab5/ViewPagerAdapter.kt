package com.example.lab5

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

// ViewPagerAdapter 繼承 FragmentStateAdapter 類別
// 傳遞 FragmentManager 和 Lifecycle 物件
class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    // 回傳 Fragment 的數量
    override fun getItemCount(): Int = 3

    // 根據 position 位置回傳對應的 Fragment
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // 第一頁 Fragment
            0 -> FirstFragment()
            // 第二頁 Fragment
            1 -> SecondFragment()
            // 第三頁 Fragment
            else -> ThirdFragment()
        }
    }
}