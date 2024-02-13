package com.example.natube.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        recyclerView.layoutManager = LinearLayoutManager(context)
        searchAdapter = SearchAdapter()
        recyclerView.adapter = searchAdapter

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()

            val apiKey = "AIzaSyBsiX_Etl5UmQNpfJxH8COkaOB3sQ9Q5sU"

            lifecycleScope.launch {
                searchViewModel.searchVideos(query, apiKey)
            }
        }

        searchViewModel.getSearchResults().observe(viewLifecycleOwner, { results ->
            searchAdapter.searchResults = results
        })

        return binding.root
    }

}
