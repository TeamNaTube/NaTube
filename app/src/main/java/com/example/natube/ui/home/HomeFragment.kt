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
        list.add(HomeWidget.TitleWidget("카테고리"))

        var categoryList = listOf(Category("1"), Category("10"), Category("21"), Category("31"))
        list.add(HomeWidget.CategoryWidget(categoryList))

        list.add(HomeWidget.TitleWidget("키워드"))

        homeAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}