package com.example.vocamemorizationapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class TestFragment : Fragment() {
    // 현재 페이지 위치를 받는 변수
    private var currentPage: Int = 0
    private lateinit var testCountText: TextView
    private lateinit var testCurrentWord: TextView
    private lateinit var testCurrentMeaning: TextView
    private lateinit var testNextBtn: Button

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
        // 사용할 View를 설정(fragment_test)
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        return view
    }

    // View가 생성되고 나서
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testCountText = view.findViewById(R.id.testCount_text)
        testCurrentWord = view.findViewById(R.id.testCurrentWord_text)
        testCurrentMeaning = view.findViewById(R.id.testCurrentMeaning_edit)
        testNextBtn = view.findViewById(R.id.testNext_btn)

        // TestActivity에서 초기화해둔 testWords에 접근해서 이번 암기단어리스트 가져옴
        val testWordList = (activity as? TestActivity)?.testWords?.wordList
        // TestActivity에서 초기화해둔 testWords에 접근해서 이번 암기단어의 의미리스트 가져옴
        val testMeaningList = (activity as? TestActivity)?.testWords?.meaningList
        // TestActivity에서 초기화해둔 testWords에 접근해서 이번 문제 출제 순서리스트 가져옴
        val curSequence = (activity as? TestActivity)?.testWords?.sequence?.get(currentPage)

        // 문제 출제 순서 확인용 로그
        Log.d("dkj", "문제 출제 순서: ${curSequence?.toString()}")

        // 현재 몇 번째 단어인지 표시
        testCountText.text = "${currentPage + 1}/10"
        // 현재 단어 표시
        testCurrentWord.text = testWordList?.get(curSequence ?: 0)
        // 현재 단어의 의미 작성칸 표시
        val answer = testMeaningList?.get(curSequence ?: 0)
        // 다음 버튼은 마지막 페이지인 경우 결과 화면으로 이동하는 걸 알려주는 문구로 변경
        testNextBtn.text = if (currentPage != 9) "다음" else "결과확인"

        // 다음 버튼을 누를 때
        testNextBtn.setOnClickListener {
            // TestActivity에 있는 testWords의 addResponse 함수를 호출해서 정답 여부를 저장
            (activity as? TestActivity)?.testWords?.addResponse(if (testCurrentMeaning.text.toString() == answer) 1 else 0)
            // TestActivity에 있는 moveToNextPage 함수 호출
            (activity as? TestActivity)?.moveToNextPage()
        }
    }

    override fun onDestroyView() {
        // Fragment가 사라지는지 확인용
        Log.d("dkj", "TestFragment Destroy: 222")
        super.onDestroyView()
    }

    // 클래스가 메모리에 올라갈 때, 동시에 인스터스로 힙에 올라가는 동반 객체
    companion object {
        // argument로 넘길 키값
        private const val ARG_CURRENT_PAGE = "currentPage"
        // TestFragment를 새로 생성하는 함수
        fun newInstance(position: Int): TestFragment {
            val fragment = TestFragment()
            val args = Bundle()
            args.putInt(ARG_CURRENT_PAGE, position)
            fragment.arguments = args
            return fragment
        }
    }
}