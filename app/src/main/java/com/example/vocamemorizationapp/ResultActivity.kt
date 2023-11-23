package com.example.vocamemorizationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val wordResults = intent.getStringArrayListExtra("wordResults")
        val meaningResults = intent.getStringArrayListExtra("meaningResults")

        wordResults?.toString()?.let { Log.d("dkj", it) }
        meaningResults?.toString()?.let { Log.d("dkj", it) }
    }
}