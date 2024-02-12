package com.example.natube.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.natube.MyChannelPreferencesManager
import com.example.natube.R
import com.example.natube.databinding.ActivityEditChannelBinding
import com.example.natube.ui.myvideo.MyChannel

class EditChannelActivity : AppCompatActivity() {

    // 뷰 바인딩 및 변수 초기화
    private lateinit var binding: ActivityEditChannelBinding

    // 뷰 모델
    private val editViewModel: EditChannelActivityViewModel by viewModels()

    // sharedpref에 저장된 모든 정보 가져오기
    private var allInfo = MyChannelPreferencesManager.getAll<MyChannel>()

    // 내 채널 정보 변수들
    private lateinit var myInfo: MyChannel
    private lateinit var name: String
    private lateinit var description: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        editViewModel.nameErrorMessage.observe(this) {
            when {
                getString(it.message).isEmpty() -> setCompleteButton()
                else -> binding.etActivityEditChannelChannelName.error = getString(it.message)
            }
        }
    }

    private fun setCompleteButton() {
        binding.btnActivityEditChannelComplete.isEnabled = true
        binding.btnActivityEditChannelComplete.setOnClickListener {
            myInfo = MyChannel(name,null,null,description)
            MyChannelPreferencesManager.put(myInfo,myInfo.myChannelName!!)
            finish()
        }
    }

    private fun initView() {
        binding.btnActivityEditChannelComplete.isEnabled = false
        LikedItemPreferencesManager.with(this)
        getMyChannelInfo()
        setListener()

    }

    private fun setListener() {

        //LikedItemPreferencesManager.put(it, it.thumbnailsUrl)

        with(binding) {
            etActivityEditChannelChannelName.addTextChangedListener {
                name = it.toString()
                editViewModel.validateName(it.toString())
            }

            etActivityEditChannelChannelDescription.addTextChangedListener{
                description = it.toString()
            }
        }
    }

    private fun getMyChannelInfo() {

        if (intent.hasExtra("my Channel Info")) {
            myInfo = intent.getParcelableExtra<MyChannel>("my Channel Info")!!
            setView()
        } else {
            myInfo = MyChannel(null, null, null, null)
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