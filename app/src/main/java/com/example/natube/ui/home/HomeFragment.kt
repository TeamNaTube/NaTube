package com.example.natube.ui.home

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.SharedViewModel
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(homeViewModel) }

    private val homeViewModel: HomeViewModel by viewModels()
    val sharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var mContext: Context



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
        with(homeViewModel) {
            mCategoryList.observe(viewLifecycleOwner) {
                homeViewModel.fetchSearchVideoByCategory()
                viewDummyData()
            }
            mUnifiedItemList.observe(viewLifecycleOwner) {
                viewDummyData()
            }
// getting selected items in either category rv or keyword rv
            selectedItem.observe(viewLifecycleOwner) {

                val detailIntent = Intent(activity, VideoDetailActivity::class.java)
                detailIntent.putExtra("selected item", it)
                startActivity(detailIntent)

            }

        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    private fun viewDummyData() {

        var list = mutableListOf<HomeWidget>()
        var categoryList = homeViewModel.mCategoryList.value!!
        var videoList = homeViewModel.mUnifiedItemList.value!!
        /**
         *  카테고리 부분
         */

        // 타이틀
        list.add(HomeWidget.TitleWidget("카테고리"))

        // 버튼 리스트
        list.add(HomeWidget.CategoryWidget(categoryList))

        // 비디오 리스트
        list.add(HomeWidget.ListCategoryVideoItemWidget(videoList))

        /**
         *  키워드 부분
         */

        // 타이틀
        list.add(HomeWidget.TitleWidget("키워드"))

        // 버튼 리스트
        list.add(HomeWidget.CategoryWidget(categoryList))

        // 비디오 리스트
        list.add(HomeWidget.ListKeywordVideoItemWidget(videoList + videoList))


        homeAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}