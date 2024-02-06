package com.example.natube

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.natube.databinding.ActivityVideoDetailBinding

class VideoDetailActivity : AppCompatActivity() {

    // 뷰 바인딩 및 변수 초기화
    private lateinit var binding: ActivityVideoDetailBinding
    private lateinit var mContext: Context

    // 뷰 모델
    private val viewModel: VideoDetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        TODO("Not yet implemented")
    }

    private fun initView() {
        TODO("Not yet implemented")
    }
}