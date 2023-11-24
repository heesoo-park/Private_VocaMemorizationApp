package com.example.vocamemorizationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn: ImageView = findViewById(R.id.start_btn)
        // 암기 페이지로 넘어가기 위한 버튼 클릭 이벤트
        startBtn.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, StudyActivity::class.java)
            startActivity(intent)
        }
    }
}