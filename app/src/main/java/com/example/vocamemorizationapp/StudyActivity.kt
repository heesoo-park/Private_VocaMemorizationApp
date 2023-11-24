package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class StudyActivity : AppCompatActivity() {
    // ViewPager 사용을 위해 선언
    private lateinit var viewPager: ViewPager2
    // 사용가능한 단어집
    private val wordsText = listOf(
        listOf("culture", "문화"), listOf("giraffe", "기린"), listOf("experience", "경험"), listOf("hawk", "매"),
        listOf("education", "교육"), listOf("pigeon", "비둘기"), listOf("symbol", "상징"), listOf("bowl", "사발"),
        listOf("effect", "영향"), listOf("scene", "장면"), listOf("liberty", "자유"), listOf("life", "생명"),
        listOf("affair", "사건"), listOf("earth", "지구"), listOf("comfort", "안락"), listOf("pill", "알약"),
        listOf("tradition", "전통"), listOf("math", "수학"),
    )
    // StudyFragment로 필요한 값들을 넘겨주기 위해 public으로 초기화한 StudyWords 객체
    // 위의 단어집에서 랜덤으로 10개만 골라주는 getTenWords를 통과하여 10개의 단어를 저장해놓는다.
    val studyWords = StudyWords(getTenWords(wordsText))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        // ViewPager 사용을 위해 초기화
        viewPager = findViewById(R.id.studyView_pager)
        // FragmentStateAdapter를 상속받는 어댑터로 설정
        viewPager.adapter = StudyViewPagerAdapter(this)
        // 사용자가 임의로 화면을 슬라이드할 수 없도록 설정
        viewPager.isUserInputEnabled = false
        // 최대로 가질 수 있는 페이지의 개수를 양옆 하나로 제한 설정
        viewPager.offscreenPageLimit = 1
    }

    // 다음 페이지(프래그먼트)으로 넘어가는 함수(마지막 페이지에서는 다음 화면으로 넘어간다.)
    // 프래그먼트 내에서 호출하게 된다.
    fun moveToNextPage() {
        // 마지막 페이지인 경우
        if (viewPager.currentItem == 9) {
            // 테스트 화면로 이동
            val intent = Intent(this@StudyActivity, TestActivity::class.java)
            // 암기 화면에서 사용된 단어와 단어의 의미를 넘겨줌
            intent.putStringArrayListExtra("wordResults", ArrayList(studyWords.wordList))
            intent.putStringArrayListExtra("meaningResults", ArrayList(studyWords.meaningList))
            startActivity(intent)
            // 암기 페이지는 종료
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

    // 단어집에서 임의로 10개를 골라 반환해주는 함수
    private fun getTenWords(wordsText: List<List<String>>): List<List<String>> {
        return wordsText.shuffled().take(10)
    }
}

class StudyWords(pickedWords: List<List<String>>) {
    var wordList: List<String> = listOf()
    var meaningList: List<String> = listOf()

    init {
        // List<List<String>> 타입을 map 함수를 이용해 단어 부분과 단어의 의미부분을 나눠 저장
        for (item in pickedWords) {
            wordList = pickedWords.map { it[0] }
            meaningList = pickedWords.map { it[1] }
        }
    }
}