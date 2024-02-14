package com.example.natube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        viewModel.item.observe(this) {
            itemDetail = it!!
            Log.d("happyVideoDetail", "^^ ${itemDetail.isLike} is it updated from viewmodel?")
//            getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit().clear().apply()

            when(it.isLike) {
                true -> LikedItemPreferencesManager.put(it, it.thumbnailsUrl)
                false -> LikedItemPreferencesManager.remove(it, it.thumbnailsUrl)
            }
        }
    }

    private fun initView() {
        LikedItemPreferencesManager.with(this)
        getSelectedItem()
        setUpListeners()


    }

    private fun setUpListeners() {
        setBackButton()
        setLikeButton()
        setShareButton()
    }

    private fun setShareButton() {
        // TODO
    }

    private fun setLikeButton() {
        binding.ibActivityDetailBtnLike.setOnClickListener{
            when (LikedItemPreferencesManager.findItem<UnifiedItem>(itemDetail.thumbnailsUrl)) {
                null -> {
                    binding.ibActivityDetailBtnLike.setImageResource(R.drawable.ic_filled_heart)
                    viewModel.addLike(itemDetail)
                }
                else -> {
                    binding.ibActivityDetailBtnLike.setImageResource(R.drawable.ic_empty_heart)
                    viewModel.removeLike(itemDetail)
                }

            }
        }
    }


    private fun setBackButton() {
        binding.btnActivityDetailBack.setOnClickListener{
            intentLike(itemDetail)
            Log.d("happyVideoDetail", "^^ ${itemDetail.isLike} right before closing activity (intenting)")

        }
    }

    private fun intentLike(item: UnifiedItem?) {
        val itemIntent = Intent(this, MainActivity::class.java)
        itemIntent.putExtra("selected item", item)
        setResult(RESULT_OK, itemIntent)
        finish()
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

            when (LikedItemPreferencesManager.findItem<UnifiedItem>(itemDetail.thumbnailsUrl)) {
                null -> ibActivityDetailBtnLike.setImageResource(R.drawable.ic_empty_heart)
                else -> ibActivityDetailBtnLike.setImageResource(R.drawable.ic_filled_heart)
            }
        }

        val originalTitle = itemDetail.videoTitle
        val decodedTitle = originalTitle.replace("&#39;", "'")
        binding.tvActivityDetailTitle.text = decodedTitle

    }
}