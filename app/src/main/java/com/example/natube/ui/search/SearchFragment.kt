package com.example.natube.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.R
import com.example.natube.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView: RecyclerView = binding.rvSearchResult

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager

        searchAdapter = SearchAdapter()
        recyclerView.adapter = searchAdapter

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()


            lifecycleScope.launch {
                searchViewModel.searchVideos(query)
                animateButton()
            }
            hideKeyboard()
            binding.etSearch.clearFocus()
        }

        searchViewModel.getSearchResults().observe(viewLifecycleOwner, { results ->
            searchAdapter.searchResults = results
        })

        return binding.root
    }

    private fun animateButton() {
        val anim = createAlphaAnimation(1.0f, 0.7f)
        binding.btnSearch.startAnimation(anim)

        // 버튼 색 변경
        binding.btnSearch.setBackgroundResource(R.drawable.button_background)
    }

    private fun createAlphaAnimation(fromAlpha: Float, toAlpha: Float): Animation {
        val alphaAnimation = AlphaAnimation(fromAlpha, toAlpha)
        alphaAnimation.duration = 100
        alphaAnimation.repeatCount = 1
        alphaAnimation.repeatMode = AlphaAnimation.REVERSE
        return alphaAnimation
    }

    // 키보드 내리기
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}
