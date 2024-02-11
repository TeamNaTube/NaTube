package com.example.natube.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.SharedViewModel
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentHomeBinding
import com.example.natube.ui.settingchips.SettingChipsDialog


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(homeViewModel) }

    //    private val homeViewModel: HomeViewModel by viewModels()
    val sharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var mContext: Context


    private val homeViewModel: HomeViewModel by activityViewModels()

    private var isOpenApp = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
//        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvFragmentHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initView()

        initViewModel()




    }

    private fun initView() {
        setViewModelValues()
        setButtonOnClick()
    }

    private fun setViewModelValues() {
        homeViewModel.getSelectedItem(null)
    }

    private fun setButtonOnClick() {
        // 다이얼로그 수정 버튼
        binding.ivSettingChips.setOnClickListener {
            val dialog = SettingChipsDialog()
            dialog.show(childFragmentManager, "SettingChipsDialog")
        }
        binding.btnWaringSettingChipsBtn.setOnClickListener {
            val dialog = SettingChipsDialog()
            dialog.show(childFragmentManager, "SettingChipsDialog")
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun initViewModel() {
        with(homeViewModel) {
            /**
             *  미설정 일때 마다 다이얼 로그 부르기
             */
            isPrefEmpty.observe(viewLifecycleOwner) { isPrefEmpty ->
                if (isPrefEmpty) {
                    if (isOpenApp) {
                        val dialog = SettingChipsDialog()
                        dialog.show(childFragmentManager, "SettingChipsDialog")
                        isOpenApp = false
                    }
                    binding.rvFragmentHome.visibility = View.GONE
                    binding.clWaringView.visibility = View.VISIBLE
                } else {
                    binding.rvFragmentHome.visibility = View.VISIBLE
                    binding.clWaringView.visibility = View.GONE
                }
            }

            /**
             *  Category 부분 관찰
             */
            mItemByCategoryList.observe(viewLifecycleOwner) {
                updateUI()
            }

            mSelectedCategoryList.observe(viewLifecycleOwner) {
                fetchSearchVideoByCategory()
            }
            /**
             *  Keyword 부분 관찰
             */

            mKeywordList.observe(viewLifecycleOwner) {
                updateUI()
            }

            mItemByKeywordList.observe(viewLifecycleOwner) {
                updateUI()
            }
// getting selected items in either category rv or keyword rv
            selectedItem.observe(viewLifecycleOwner) {

                when (it) {
                    null -> Unit
                    else -> {
                        val detailIntent = Intent(activity, VideoDetailActivity::class.java)
                        detailIntent.putExtra("selected item", it)
                        startActivity(detailIntent)
                    }
                }

            }

        }
        /**
         *  실제 Keyword 검색 하는 부분(할당량 때문에 주석 처리)
         */
        homeViewModel.keywordQuery.observe(viewLifecycleOwner) {
//                fetchSearchVideoByKeyword()
        }
    }


    private fun updateUI() {

        val list = mutableListOf<HomeWidget>()
        val categoryList = homeViewModel.mSelectedCategoryList.value ?: listOf()
        val categoryVideoList = homeViewModel.mItemByCategoryList.value ?: listOf()
        val keywordList = homeViewModel.mKeywordList.value ?: listOf()
        val keywordVideoList = homeViewModel.mItemByKeywordList.value ?: listOf()
        /**
         *  카테고리 부분
         */

        // 타이틀
        list.add(HomeWidget.TitleWidget("카테고리"))

        // 버튼 리스트
        list.add(HomeWidget.ChipWidget(categoryList))

        // 비디오 리스트
        list.add(HomeWidget.ListCategoryVideoItemWidget(categoryVideoList))

        /**
         *  키워드 부분
         */

        // 타이틀
        list.add(HomeWidget.TitleWidget("키워드"))

        // 버튼 리스트
        list.add(HomeWidget.ChipWidget(keywordList))

        // 비디오 리스트
        list.add(HomeWidget.ListKeywordVideoItemWidget(keywordVideoList))

        homeAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}