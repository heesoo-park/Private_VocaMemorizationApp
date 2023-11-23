package com.example.vocamemorizationapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StudyViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        return StudyFragment.newInstance(position)
    }
}