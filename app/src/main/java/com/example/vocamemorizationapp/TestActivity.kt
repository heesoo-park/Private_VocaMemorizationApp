package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    lateinit var testWords: TestWords

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val wordResults = intent.getStringArrayListExtra("wordResults")?.toList()
        val meaningResults = intent.getStringArrayListExtra("meaningResults")?.toList()

        wordResults?.toString()?.let { Log.d("dkj", it) }
        meaningResults?.toString()?.let { Log.d("dkj", it) }

        viewPager = findViewById(R.id.testView_pager)
        viewPager.adapter = TestViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 1

        testWords = TestWords(wordResults!!, meaningResults!!)
    }

    fun moveToNextPage() {
        if (viewPager.currentItem == 9) {
            val intent: Intent = Intent(this@TestActivity, ResultActivity::class.java)
            intent.putStringArrayListExtra("wordResults", ArrayList(testWords.wordList))
            intent.putStringArrayListExtra("meaningResults", ArrayList(testWords.meaningList))
            intent.putIntegerArrayListExtra("sequence", ArrayList(testWords.sequence))
            intent.putIntegerArrayListExtra("testResults", ArrayList(testWords.results))
            startActivity(intent)
            finish()
        } else {
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
    val sequence = (0..9).shuffled()
    val results = mutableListOf<Int>()

    fun addResponse(response: Int) {
        Log.d("dkj", "addResponse: $response")
        response.let { results.add(it) }
    }
}