package com.example.natube.ui.myvideo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.natube.databinding.VideoItemBinding
import com.example.natube.model.UnifiedItem

class MyVideoAdapter : ListAdapter<UnifiedItem, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("Happy_TagRv", "^^ 1. 바인딩 잘됨")
        return MyVideoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("Happy_TagRv", "^^ 2. 바인드 뷰홀더 실행")

        val item = getItem(position)
        Log.d("Happy_TagRv", "^^ 3. 겟 아이템 $item")

        (holder as MyVideoItemViewHolder).apply{
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

    inner class MyVideoItemViewHolder(binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // 아이템
        var cl_video_item: ConstraintLayout = binding.clVideoItem

        // 이미지
        val ivVideoItemThumbnail = binding.ivVideoItemThumbnail

        // 텍스트
        val tvVideoItemTitle = binding.tvVideoItemTitle
        val tvVideoItemChannelName = binding.tvVideoItemChannelName
        val tvVideoItemUploadTime = binding.tvVideoItemUploadDate

        init {
            cl_video_item.setOnClickListener(this)
        }

        // 클릭 이벤트 처리
        override fun onClick(p0: View?) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = getItem(position)
            Log.d("HappyListVideoItemAdapter", "^^onClicked")
            // TODO ??
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UnifiedItem>() {
            override fun areItemsTheSame(oldItem: UnifiedItem, newItem: UnifiedItem): Boolean {
                Log.d("Happy_TagRv","^^ItemstheSame?")
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UnifiedItem, newItem: UnifiedItem): Boolean {
                Log.d("Happy_TagRv","^^ContentstheSame?")
                return oldItem == newItem
            }
        }
    }


}