package com.example.natube.ui.settingchips

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.R
import com.example.natube.AnimationView
import com.example.natube.databinding.DialogSettingChipsBinding
import com.example.natube.databinding.VideoCategoryBinding
import com.example.natube.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class SettingChipsDialog : DialogFragment() {
    private lateinit var binding: DialogSettingChipsBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val keywordAdapter by lazy { KeywordAdapter(homeViewModel) }

    private var isShotDown = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogSettingChipsBinding.inflate(inflater, container, false)
        with(binding) {
            cgCategoryChips.apply {
                homeViewModel.mCategoryList.value?.forEachIndexed { index, chip ->
                    // 각 반복마다 새로운 Chip 인스턴스를 생성
                    val chipBinding = VideoCategoryBinding.inflate(inflater, null, false)
                    chipBinding.chipCategory.apply {
                        isChecked = chip.isClicked
                        text = chip.name

                        if (isChecked) setChipBackgroundColorResource(R.color.green)
                        else setChipBackgroundColorResource(R.color.grey)

                        setOnClickListener {
                            AnimationView.shakeView(it)
                            homeViewModel.isClickedItem(index)

                            if (isChecked) setChipBackgroundColorResource(R.color.green)
                            else setChipBackgroundColorResource(R.color.grey)
                        }
                    }
                    // 생성된 Chip 인스턴스를 ChipGroup에 추가
                    addView(chipBinding.root)
                }
            }
            rvKeywordChips.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = keywordAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(homeViewModel) {
            //최초 실행
            backupChipList()
            checkedValidate()
            //관찰
            preKeywordList.observe(viewLifecycleOwner) {
                keywordAdapter.submitList(it)
            }
            isValidated.observe(viewLifecycleOwner) { isChecked ->
                binding.ivCheck.apply {
                    if (isChecked) {
                        setColorFilter(
                            ContextCompat.getColor(context, R.color.green)
                        )
                    } else {
                        AnimationView.shakeView(this)
                        Snackbar.make(
                            view,
                            "각 최소 1개 최대 5개 선택/추가 이(가) 가능 합니다!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        setColorFilter(Color.GRAY)
                    }

                    isClickable = isChecked
                }
            }
        }

        with(binding) {

            //키워드 추가 버튼 클릭
            btnKeyword.apply {
                setOnClickListener {
                    val query = etKeyword.text.toString()
                    when (homeViewModel.checkedQueryValidate(query)) {
                        0 -> { //유효성 검사 성공시
                            homeViewModel.addKeywordChip(query)
                            etKeyword.text = null
                            hideKeyboard()
                        }

                        1 -> {// 키워드 에 빈 문자만 있을시
                            Snackbar.make(view, "키워드가 비어 있습니다!", Snackbar.LENGTH_SHORT).show()
                        }

                        2 -> {// 이미 존재하는 키워드 일 경우
                            Snackbar.make(view, "이미 존재하는 키워드 입니다!", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            //유효성 검사 체크 버튼 클릭
            ivCheck.setOnClickListener {
                homeViewModel.saveSelectedCategoryList()
                homeViewModel.saveKeywordList()
                isShotDown = false
                dismiss()
            }
        }
    }

    // 키보드 내리기
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isShotDown) homeViewModel.rollBackChipList()

    }

}