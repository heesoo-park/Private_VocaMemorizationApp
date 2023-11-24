package com.example.vocamemorizationapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class StudyFragment: Fragment() {
    // 현재 페이지 위치를 받는 변수
    private var currentPage: Int = 0
    private lateinit var countText: TextView
    private lateinit var currentWord: TextView
    private lateinit var currentMeaning: TextView
    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // onCreate 될 때 argument로 넘어온 값을 통해 현재 페이지 위치 저장
        arguments?.let {
            currentPage = it.getInt(ARG_CURRENT_PAGE)
        }
    }

    // View가 생성되는 중
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 사용할 View를 설정(fragment_study)
        val view = inflater.inflate(R.layout.fragment_study, container, false)
        return view
    }

    // View가 생성되고 나서
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countText = view.findViewById(R.id.testCount_text)
        currentWord = view.findViewById(R.id.testCurrentWord_text)
        currentMeaning = view.findViewById(R.id.currentMeaning_text)
        nextBtn = view.findViewById(R.id.testNext_btn)

        // StudyActivity에서 초기화해둔 studyWords에 접근해서 이번 암기단어리스트 가져옴
        val wordList = (activity as? StudyActivity)?.studyWords?.wordList
        // StudyActivity에서 초기화해둔 studyWords에 접근해서 이번 암기단어의 의미리스트 가져옴
        val meaningList = (activity as? StudyActivity)?.studyWords?.meaningList

        // 현재 몇 번째 단어인지 표시
        countText.text = "${currentPage + 1}/10"
        // 현재 단어 표시
        currentWord.text = wordList?.get(currentPage) ?: ""
        // 현재 단어의 의미 표시
        currentMeaning.text = meaningList?.get(currentPage) ?: ""
        // 다음 버튼은 마지막 페이지인 경우 테스트 화면으로 이동하는 걸 알려주는 문구로 변경
        nextBtn.text = if (currentPage != 9) "다음" else "테스트하러가기"

        // 다음 버튼을 누를 때
        nextBtn.setOnClickListener {
            // StudyActivity에 있는 moveToNextPage 함수 호출
            (activity as? StudyActivity)?.moveToNextPage()
        }
    }

    override fun onDestroyView() {
        // Fragment가 사라지는지 확인용
        Log.d("dkj", "StudyFragment Destroy: 111")
        super.onDestroyView()
    }

    // 클래스가 메모리에 올라갈 때, 동시에 인스터스로 힙에 올라가는 동반 객체
    companion object {
        // argument로 넘길 키값
        private const val ARG_CURRENT_PAGE = "currentPage"
        // StudyFragment를 새로 생성하는 함수
        fun newInstance(position: Int): StudyFragment {
            val fragment = StudyFragment()
            val args = Bundle()
            args.putInt(ARG_CURRENT_PAGE, position)
            fragment.arguments = args
            return fragment
        }
    }
}