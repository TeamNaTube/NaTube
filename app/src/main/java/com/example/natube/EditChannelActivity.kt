package com.example.natube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.natube.databinding.ActivityEditChannelBinding
import com.example.natube.model.UnifiedItem
import com.example.natube.ui.myvideo.MyChannel

class EditChannelActivity : AppCompatActivity() {

    // 뷰 바인딩 및 변수 초기화
    private lateinit var binding: ActivityEditChannelBinding

    // 뷰 모델
    private val editViewModel: EditChannelActivityViewModel by viewModels()

    // 내 채널 정보 변수들
    private lateinit var myInfo: MyChannel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
    }

    private fun initView() {
        getMyChannelInfo()

    }

    private fun getMyChannelInfo() {

        if (intent.hasExtra("my Channel Info")) {
            myInfo = intent.getParcelableExtra<MyChannel>("my Channel Info")!!
            setView()
        }
    }

    private fun setView() {
        with(binding) {
            when (etActivityEditChannelChannelName.text.toString()) {
                getString(R.string.title_my_video) -> Unit
                else -> etActivityEditChannelChannelName.setText(myInfo.myChannelName.toString())
            }
            when (etActivityEditChannelChannelDescription.text.toString()) {
                getString(R.string.user_description) -> Unit
                else -> etActivityEditChannelChannelDescription.setText(myInfo.myChannelDescription.toString())
            }
        }
    }
}