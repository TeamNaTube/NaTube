package com.example.natube.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natube.databinding.FragmentHomeBinding
import com.example.natube.model.Category
import com.example.natube.model.UnifiedItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeAdapter = HomeAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvFragmentHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var list = mutableListOf<HomeWidget>()
        var categoryList = listOf(Category("1"), Category("10"), Category("21"), Category("31"))
        var dummyVideoItem = UnifiedItem(
            videoTitle = "비디오 이름",
            channelTitle = "채널명",
            description = "",
            dateTime = "20240206",
            thumbnailsUrl = "",
            categoryId = ""
        )
        var videoList = listOf(
            dummyVideoItem, dummyVideoItem, dummyVideoItem, dummyVideoItem, dummyVideoItem
        )
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
        list.add(HomeWidget.CategoryWidget(categoryList.shuffled()))

        // 비디오 리스트
        list.add(HomeWidget.ListKeywordVideoItemWidget(videoList+videoList))


        homeAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}