package com.example.vocamemorizationapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TestViewPagerAdapter(fragmentActivity: TestActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        return TestFragment.newInstance(position)
    }
}