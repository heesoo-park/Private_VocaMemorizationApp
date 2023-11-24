package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class ResultActivity : AppCompatActivity() {

    // 틀린 단어 목록 보여주는 TextView들 한꺼번에 조절하기 위해 List<TextView>로 선언
    private val wrongWordTextView: List<TextView> by lazy {
        listOf(
            findViewById(R.id.wrongWord1_text),
            findViewById(R.id.wrongWord2_text),
            findViewById(R.id.wrongWord3_text),
            findViewById(R.id.wrongWord4_text),
            findViewById(R.id.wrongWord5_text),
            findViewById(R.id.wrongWord6_text),
            findViewById(R.id.wrongWord7_text),
            findViewById(R.id.wrongWord8_text),
            findViewById(R.id.wrongWord9_text),
            findViewById(R.id.wrongWord10_text),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // TestActivty에서 넘어온 단어, 단어의 의미, 랜덤 순서, 테스트 결과들 받기
        val wordResults = intent.getStringArrayListExtra("wordResults")
        val meaningResults = intent.getStringArrayListExtra("meaningResults")
        val sequence = intent.getIntegerArrayListExtra("sequence")
        val testResults = intent.getIntegerArrayListExtra("testResults")

        // 잘 넘어왔는지 확인용 코드
        wordResults?.toString()?.let { Log.d("dkj", it) }
        meaningResults?.toString()?.let { Log.d("dkj", it) }
        sequence?.toString()?.let { Log.d("dkj", it) }
        testResults?.toString()?.let { Log.d("dkj", it) }

        val scoreText: TextView = findViewById(R.id.score_text)
        val backBtn: Button = findViewById(R.id.back_btn)

        // 맞춘 개수는 1의 개수를 세서 출력
        val score: Int = testResults!!.count { it == 1 }
        scoreText.text = "${score}/10"

        // 초기 세팅을 gone으로 했던 TextView들을 틀린문제만큼만 화면에 출력하기 위한 코드
        var count = 0
        for (i in testResults.indices) {
            // 오답이라면
            if (testResults[i] == 0) {
                val wrongWord = wordResults?.get(sequence?.get(i) ?: 0)
                val wrongMeaning = meaningResults?.get(sequence?.get(i) ?: 0)
                // 해당 단어와 단어의 의미를 출력
                wrongWordTextView[count].text = "${wrongWord} : ${wrongMeaning}"
                // 화면에 띄우기
                wrongWordTextView[count++].isVisible = true
            }
        }

        // 처음화면으로 버튼 클릭 이벤트
        backBtn.setOnClickListener {
            // 메인 화면으로 이동
            val intent: Intent = Intent(this@ResultActivity, MainActivity::class.java)
            // 모든 액티비티 초기화 및 새로 시작
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}