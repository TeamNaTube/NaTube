package com.example.natube

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.natube.databinding.ActivityVideoDetailBinding
import com.example.natube.model.UnifiedItem

class VideoDetailActivity : AppCompatActivity() {

    // 뷰 바인딩 및 변수 초기화
    private lateinit var binding: ActivityVideoDetailBinding
//    private lateinit var mContext: Context

    // 뷰 모델
    private val viewModel: VideoDetailActivityViewModel by viewModels()

    // 선택된 아이템 받아오기
//    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    // 선택된 아이템 지연초기화
    private lateinit var itemDetail: UnifiedItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initViewModel() {

    }

    private fun initView() {
        getSelectedItem()
        setBackButton()
    }

    private fun setBackButton() {
        binding.btnActivityDetailBack.setOnClickListener{
            val returnIntent: Intent = intent
            intent.putExtra("message", true)
            setResult(Activity.RESULT_OK,returnIntent)
            finish()
        }
    }

    private fun getSelectedItem() {
        if (intent.hasExtra("selected item")) {
            itemDetail = intent.getParcelableExtra<UnifiedItem>("selected item")!!
            setView()
        }


//        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult ()) {
//            if (it.resultCode == RESULT_OK) {
//                val address = it.data?.getParcelableExtra<UnifiedItem>("selected item")
//                Log.d("happyvideoDetailactivity", "^^ $address")
//                setView(address)
//            }
//        }
//
////        val intent = Intent(this, MainActivity::class.java)
////        activityResultLauncher.launch(intent)
    }

    private fun setView() {

        Log.d("happyvideoDetailactivity", "^^ $itemDetail")

        Glide.with(this).load(itemDetail?.thumbnailsUrl).into(binding.ivActivityDetailThumbnail)

        with(binding) {
            tvActivityDetailTitle.text = itemDetail.videoTitle
            tvActivityDetailChannelName.text = itemDetail.channelTitle
            tvActivityDetailDescription.text = itemDetail.description
            tvActivityDetailUploadDate.text = itemDetail.dateTime
        }

    }
}