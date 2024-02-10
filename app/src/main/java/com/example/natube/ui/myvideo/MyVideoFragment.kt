package com.example.natube.ui.myvideo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.natube.ModelPreferencesManager
import com.example.natube.databinding.FragmentMyVideosBinding
import com.example.natube.model.UnifiedItem

class MyVideoFragment : Fragment() {

    private var _binding: FragmentMyVideosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val likedItems = ModelPreferencesManager.getAll<UnifiedItem>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("HappyMyVideo","^^ from sharedpref $likedItems")
        val myVideoViewModel =
            ViewModelProvider(this)[MyVideoViewModel::class.java]

        _binding = FragmentMyVideosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setMyVideoAdapter()


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLikedVideos()
    }

    private fun setMyVideoAdapter() {
        val myVideoAdapter = MyVideoAdapter()
        binding.rvFragmentMyVideoFavourites.adapter = myVideoAdapter

        Log.d("happyMyVideoFragment_TagRV","^^get SHaredPref ? ${likedItems.size}")
        myVideoAdapter.submitList(likedItems)

    }

    private fun setLikedVideos() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}