package com.example.natube.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.VideoItemBinding
import com.example.natube.model.UnifiedItem
import com.bumptech.glide.Glide
import com.example.natube.VideoDetailActivity

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    var searchResults: List<UnifiedItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class SearchViewHolder(private val binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // 아이템
        var cl_video_item = binding.clVideoItem

        // 이미지
        val ivVideoItemThumbnail = binding.ivVideoItemThumbnail

        // 텍스트
        val tvVideoItemTitle = binding.tvVideoItemTitle
        val tvVideoItemChannelName = binding.tvVideoItemChannelName
        val tvVideoItemUploadTime = binding.tvVideoItemUploadDate

        init {
            cl_video_item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            // 아이템 클릭 시 동작 정의
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = searchResults[position]

                // VideoDetailActivity로 이동
                val intent = Intent(v?.context, VideoDetailActivity::class.java)
                intent.putExtra("selected item", item)
                v?.context?.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchResults[position]

        // 데이터를 뷰에 바인딩
        Glide.with(holder.itemView.context).load(item.thumbnailsUrl).into(holder.ivVideoItemThumbnail)
        holder.tvVideoItemTitle.text = item.videoTitle
        holder.tvVideoItemChannelName.text = item.channelTitle
        holder.tvVideoItemUploadTime.text = item.dateTime
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }
}
