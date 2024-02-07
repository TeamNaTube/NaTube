package com.example.natube.ui.settingchips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.natube.databinding.DialogSettingChipsBinding
import com.example.natube.ui.home.HomeViewModel

class SettingChipsDialog : DialogFragment() {
    private lateinit var binding: DialogSettingChipsBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val categoryAdapter by lazy{ CategoryAdapter(homeViewModel)}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogSettingChipsBinding.inflate(inflater, container, false)
        binding.rvCategoryChips.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        homeViewModel.mCategoryList.observe(viewLifecycleOwner){
            val list = homeViewModel.mCategoryList.value
            categoryAdapter.submitList(list)
        }
    }

}