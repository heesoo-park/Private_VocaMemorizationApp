package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {
    // ViewPager 사용을 위해 선언
    private lateinit var viewPager: ViewPager2
    // 테스트에 사용될 단어와 의미 리스트를 저장하고 이를 프래그먼트에 보내주는 TestWords 객체
    // 프래그먼트에서 보내주는 값도 이걸 통해 받음
    lateinit var testWords: TestWords

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // StudyActivty에서 넘어온 단어와 단어의 의미들 받기
        val wordResults = intent.getStringArrayListExtra("wordResults")?.toList()
        val meaningResults = intent.getStringArrayListExtra("meaningResults")?.toList()

        // 잘 넘어왔는지 확인용 코드
        wordResults?.toString()?.let { Log.d("dkj", it) }
        meaningResults?.toString()?.let { Log.d("dkj", it) }

        // ViewPager 사용을 위해 초기화
        viewPager = findViewById(R.id.testView_pager)
        // FragmentStateAdapter를 상속받는 어댑터로 설정
        viewPager.adapter = TestViewPagerAdapter(this)
        // 사용자가 임의로 화면을 슬라이드할 수 없도록 설정
        viewPager.isUserInputEnabled = false
        // 최대로 가질 수 있는 페이지의 개수를 양옆 하나로 제한 설정
        viewPager.offscreenPageLimit = 1

        // lateinit var로 설정했던 testWords 초기화
        testWords = TestWords(wordResults!!, meaningResults!!)
    }

    // 다음 페이지(프래그먼트)으로 넘어가는 함수(마지막 페이지에서는 다음 화면으로 넘어간다.)
    // 프래그먼트 내에서 호출하게 된다.
    fun moveToNextPage() {
        // 마지막 페이지인 경우
        if (viewPager.currentItem == 9) {
            // 결과 화면로 이동
            val intent: Intent = Intent(this@TestActivity, ResultActivity::class.java)
            // 테스트 화면에서 사용된 단어, 단어의 의미, 랜덤 순서, 테스트 결과를 넘겨줌
            intent.putStringArrayListExtra("wordResults", ArrayList(testWords.wordList))
            intent.putStringArrayListExtra("meaningResults", ArrayList(testWords.meaningList))
            intent.putIntegerArrayListExtra("sequence", ArrayList(testWords.sequence))
            intent.putIntegerArrayListExtra("testResults", ArrayList(testWords.results))
            startActivity(intent)
            // 테스트 페이지는 종료
            finish()
        }
        // 마지막 페이지가 아닌 경우
        else {
            // 현재 페이지에서 1을 더해 setCurrentItem을 사용하면 자연스럽게 다음 페이지로 슬라이드됨
            val nextPage = viewPager.currentItem + 1
            if (nextPage < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.setCurrentItem(nextPage, true)
            }
        }
    }
}

class TestWords(words: List<String>, meanings: List<String>) {
    val wordList = words
    val meaningList = meanings
    // 한 번 초기화되어 모든 테스트 페이지에서 이 순서를 공유함
    val sequence = (0..9).shuffled()
    val results = mutableListOf<Int>()

    // 테스트 페이지에서 입력한 값이 정답이라면 1, 오답이라면 0을 results에 저장하는 함수
    fun addResponse(response: Int) {
        response.let { results.add(it) }
    }
}