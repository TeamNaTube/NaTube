package com.example.natube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.VideoItemBinding
import com.example.natube.model.UnifiedItem

class ListVideoItemAdapter : ListAdapter<UnifiedItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UnifiedItem>() {
            override fun areItemsTheSame(oldItem: UnifiedItem, newItem: UnifiedItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UnifiedItem, newItem: UnifiedItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ListVideoItemViewHolder(binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // 이미지
        val ivVideoItemThumbnail = binding.ivVideoItemThumbnail

        // 텍스트
        val tvVideoItemTitle = binding.tvVideoItemTitle
        val tvVideoItemChannelName = binding.tvVideoItemChannelName
        val tvVideoItemUploadTime = binding.tvVideoItemUploadDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListVideoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ListVideoItemViewHolder).apply{
            //이미지
            //텍스트
            tvVideoItemTitle.text = item.videoTitle
            tvVideoItemChannelName.text = item.channelTitle
            tvVideoItemUploadTime.text = item.dateTime
        }
    }
}