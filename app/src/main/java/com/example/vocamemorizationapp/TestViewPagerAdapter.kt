package com.example.vocamemorizationapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

// 아래 두 함수는 필수로 오버라이딩해아하는 함수
// 액티비티 안에서 프래그먼트를 ViewPager로 할 때 쓰는 듯 하다.
class TestViewPagerAdapter(fragmentActivity: TestActivity): FragmentStateAdapter(fragmentActivity) {
    // 총 페이지 개수
    override fun getItemCount(): Int {
        return 10
    }

    // 프래그먼트를 새로 만드는 함수
    override fun createFragment(position: Int): Fragment {
        return TestFragment.newInstance(position)
    }
}