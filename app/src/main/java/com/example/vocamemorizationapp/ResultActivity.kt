package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class ResultActivity : AppCompatActivity() {

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

        val wordResults = intent.getStringArrayListExtra("wordResults")
        val meaningResults = intent.getStringArrayListExtra("meaningResults")
        val sequence = intent.getIntegerArrayListExtra("sequence")
        val testResults = intent.getIntegerArrayListExtra("testResults")

        wordResults?.toString()?.let { Log.d("dkj", it) }
        meaningResults?.toString()?.let { Log.d("dkj", it) }
        sequence?.toString()?.let { Log.d("dkj", it) }
        testResults?.toString()?.let { Log.d("dkj", it) }

        val scoreText: TextView = findViewById(R.id.score_text)
        val backBtn: Button = findViewById(R.id.back_btn)

        val score: Int = testResults!!.count { it == 1 }
        scoreText.text = "${score}/10"

        var count = 0
        for (i in testResults.indices) {
            if (testResults[i] == 0) {
                val wrongWord = wordResults?.get(sequence?.get(i) ?: 0)
                val wrongMeaning = meaningResults?.get(sequence?.get(i) ?: 0)

                wrongWordTextView[count].text = "${wrongWord} : ${wrongMeaning}"
                wrongWordTextView[count++].isVisible = true
            }
        }

        backBtn.setOnClickListener {
            val intent: Intent = Intent(this@ResultActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}