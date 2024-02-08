package com.example.natube.ui.settingchips

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.R
import com.example.natube.databinding.DialogSettingChipsBinding
import com.example.natube.ui.home.HomeViewModel

class SettingChipsDialog : DialogFragment() {
    private lateinit var binding: DialogSettingChipsBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val categoryAdapter by lazy { CategoryAdapter(homeViewModel) }
    private val keywordAdapter by lazy { KeywordAdapter(homeViewModel) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogSettingChipsBinding.inflate(inflater, container, false)
        with(binding) {
            rvCategoryChips.apply {
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
            }
            rvKeywordChips.apply {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter = keywordAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(homeViewModel) {
            //최초 실행
            val list = mCategoryList.value
            categoryAdapter.submitList(list)

            //관찰
            preKeywordList.observe(viewLifecycleOwner) {
                keywordAdapter.submitList(it)
            }
            isValidated.observe(viewLifecycleOwner){isChecked ->
                binding.ivCheck.apply{
                    if(isChecked){
                        setColorFilter(
                            ContextCompat.getColor(context,R.color.green))
                    }
                    else{
                        setColorFilter(Color.GRAY)
                    }

                    isClickable = isChecked
                }
            }
        }

        with(binding) {
            btnKeyword.apply {
                setOnClickListener {
                    val query = etKeyword.text.toString()
                    homeViewModel.addKeywordChip(query)
                    etKeyword.text = null

                }
            }
            ivCheck.setOnClickListener {
                homeViewModel.initSelectedCategoryList()
                homeViewModel.initKeywordList()
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}