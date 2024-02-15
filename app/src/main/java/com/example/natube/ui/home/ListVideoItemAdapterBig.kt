package com.example.natube.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.natube.databinding.VideoItemBigBinding
import com.example.natube.databinding.VideoItemBinding
import com.example.natube.model.UnifiedItem

class ListVideoItemAdapterBig(private val viewModel: HomeViewModel) : ListAdapter<UnifiedItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
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

    inner class ListVideoItemViewHolder(binding: VideoItemBigBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // 아이템
        private var clVideoItem: ConstraintLayout = binding.clVideoItemBig

        // 이미지
        val ivVideoItemThumbnail = binding.ivVideoItemThumbnailBig

        // 텍스트
        val tvVideoItemTitle = binding.tvVideoItemTitleBig
        val tvVideoItemChannelName = binding.tvVideoItemChannelNameBig
        val tvVideoItemUploadTime = binding.tvVideoItemUploadDateBig

        init {
            clVideoItem.setOnClickListener(this)
        }

        // 클릭 이벤트 처리
        override fun onClick(p0: View?) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = getItem(position)
            Log.d("HappyListVideoItemAdapter", "^^onClicked")
            viewModel.getSelectedItem(item)
        }
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            VideoItemBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListVideoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ListVideoItemViewHolder).apply{
            //이미지
            Glide.with(itemView)
                .load(item.thumbnailsUrl)
                .into(ivVideoItemThumbnail)

            //텍스트
            tvVideoItemTitle.text = item.videoTitle
            tvVideoItemChannelName.text = item.channelTitle
            tvVideoItemUploadTime.text = item.dateTime


        }
    }
}