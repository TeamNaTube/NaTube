package com.example.natube.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.R
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentSearchBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import me.ibrahimsn.lib.SmoothBottomBar

class SearchFragment : Fragment() {

    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var bottomNavigationView: SmoothBottomBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView: RecyclerView = binding.rvSearchResult

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager

        searchAdapter = SearchAdapter(searchViewModel)
        recyclerView.adapter = searchAdapter

//        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()


            lifecycleScope.launch {
                searchViewModel.searchVideos(query)
                animateButton()
            }
            hideKeyboard()
            binding.etSearch.clearFocus()
        }

        searchViewModel.getSearchResults().observe(viewLifecycleOwner) { results ->
            searchAdapter.searchResults = results
        }
        initViewModel()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BottomNavigationView 초기화
        bottomNavigationView = activity?.findViewById(R.id.nav_view) ?: return

        // 포커스 이벤트 감지
        val editTextSearch = view.findViewById<EditText>(R.id.et_search)
        editTextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 검색창에 포커스가 있을 때 BottomNavigationView 감춤
                hideBottomNavigationView()
            } else {
                // 포커스가 없을 때 BottomNavigationView 표시
                showBottomNavigationView()
            }
        }

    }
    private fun showBottomNavigationView() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNavigationView() {
        bottomNavigationView.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        searchViewModel.initializeSelectedItem()
    }

    private fun initViewModel() {
        searchViewModel.selectedItem.observe(viewLifecycleOwner) {
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
