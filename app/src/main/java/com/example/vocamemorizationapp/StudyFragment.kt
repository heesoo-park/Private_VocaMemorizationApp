package com.example.vocamemorizationapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class StudyFragment(val curPage: Int): Fragment() {

    private var currentPage: Int = 0
    private lateinit var countText: TextView
    private lateinit var currentWord: TextView
    private lateinit var currentMeaning: TextView
    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentPage = it.getInt(ARG_CURRENT_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_study, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("dkj", "onViewCreated: $curPage")
        countText = view.findViewById(R.id.count_text)
        currentWord = view.findViewById(R.id.currentWord_text)
        currentMeaning = view.findViewById(R.id.currentMeaning_text)
        nextBtn = view.findViewById(R.id.next_btn)

        val wordList = (activity as? StudyActivity)?.studyWords?.wordList
        val meaningList = (activity as? StudyActivity)?.studyWords?.meaningList

        countText.text = "${currentPage + 1}/10"
        currentWord.text = wordList?.get(currentPage) ?: ""
        currentMeaning.text = meaningList?.get(currentPage) ?: ""
        nextBtn.text = if (currentPage != 9) "다음" else "테스트하러가기"

        nextBtn.setOnClickListener {
            (activity as? StudyActivity)?.moveToNextPage()

        }
    }

    companion object {
        private const val ARG_CURRENT_PAGE = "currentPage"

        fun newInstance(currentPage: Int): StudyFragment {
            val fragment = StudyFragment(currentPage)
            val args = Bundle()
            args.putInt(ARG_CURRENT_PAGE, currentPage)
            fragment.arguments = args
            return fragment
        }
    }
}