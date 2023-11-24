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

    private var currentPage: Int = 0
    private lateinit var testCountText: TextView
    private lateinit var testCurrentWord: TextView
    private lateinit var testCurrentMeaning: TextView
    private lateinit var testNextBtn: Button

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
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testCountText = view.findViewById(R.id.testCount_text)
        testCurrentWord = view.findViewById(R.id.testCurrentWord_text)
        testCurrentMeaning = view.findViewById(R.id.testCurrentMeaning_edit)
        testNextBtn = view.findViewById(R.id.testNext_btn)

        val testWordList = (activity as? TestActivity)?.testWords?.wordList
        val testMeaningList = (activity as? TestActivity)?.testWords?.meaningList
        val curSequence = (activity as? TestActivity)?.testWords?.sequence?.get(currentPage)
        Log.d("dkj", "이번 테스트 순서: ${curSequence?.toString()}")

        testCountText.text = "${currentPage + 1}/10"
        testCurrentWord.text = testWordList?.get(curSequence ?: 0)
        val answer = testMeaningList?.get(curSequence ?: 0)
        testNextBtn.text = if (currentPage != 9) "다음" else "결과확인"

        testNextBtn.setOnClickListener {
            Log.d("dkj", "${testCurrentMeaning.text} : $answer")
            (activity as? TestActivity)?.testWords?.addResponse(if (testCurrentMeaning.text.toString() == answer) 1 else 0)
            (activity as? TestActivity)?.moveToNextPage()
        }
    }

    override fun onDestroyView() {
        Log.d("dkj", "TestFragment Destroy: 222")
        super.onDestroyView()
    }

    companion object {
        private const val ARG_CURRENT_PAGE = "currentPage"

        fun newInstance(position: Int): TestFragment {
            val fragment = TestFragment()
            val args = Bundle()
            args.putInt(ARG_CURRENT_PAGE, position)
            fragment.arguments = args
            return fragment
        }
    }
}