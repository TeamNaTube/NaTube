package com.example.natube.ui.settingchips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.AnimationView
import com.example.natube.databinding.VideoKeywordBinding
import com.example.natube.model.Chip
import com.example.natube.ui.home.HomeViewModel

class KeywordAdapter(private val viewModel: HomeViewModel) : ListAdapter<Chip, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Chip>() {
            override fun areItemsTheSame(oldItem: Chip, newItem: Chip): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Chip, newItem: Chip): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class KeywordViewHolder(binding: VideoKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val chip = binding.chipKeyword
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            VideoKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as KeywordViewHolder).apply {
            chip.apply{
                text = item.name
                setOnCloseIconClickListener {
                    AnimationView.shakeView(it)
                    // 클릭시 제거
                    viewModel.deleteKeywordChip(item)
                }
            }

        }
    }
}