package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class StudyActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private val wordsText = listOf(
        listOf("culture", "문화"), listOf("giraffe", "기린"), listOf("experience", "경험"), listOf("hawk", "매"),
        listOf("education", "교육"), listOf("pigeon", "비둘기"), listOf("symbol", "상징"), listOf("bowl", "사발"),
        listOf("effect", "영향"), listOf("scene", "장면"), listOf("liberty", "자유"), listOf("life", "생명"),
        listOf("affair", "사건"), listOf("earth", "지구"), listOf("comfort", "안락"), listOf("pill", "알약"),
        listOf("tradition", "전통"), listOf("math", "수학"),
    )

    val studyWords = StudyWords(getTenWords(wordsText))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = StudyViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
    }

    fun moveToNextPage() {
        if (viewPager.currentItem == 9) {
            val intent = Intent(this@StudyActivity, ResultActivity::class.java)
            intent.putStringArrayListExtra("wordResults", ArrayList(studyWords.wordList))
            intent.putStringArrayListExtra("meaningResults", ArrayList(studyWords.meaningList))
            startActivity(intent)
            finish()
        } else {
            val nextPage = viewPager.currentItem + 1
            if (nextPage < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.setCurrentItem(nextPage, true)

            }
        }
    }

    fun getTenWords(wordsText: List<List<String>>): List<List<String>> {
        val tenWords = wordsText.shuffled().take(10)

        return tenWords
    }
}

class StudyWords(pickedWords: List<List<String>>) {
    var wordList: List<String> = listOf()
    var meaningList: List<String> = listOf()

    init {
        for (item in pickedWords) {
            wordList = pickedWords.map { it[0] }
            meaningList = pickedWords.map { it[1] }
        }
    }
}