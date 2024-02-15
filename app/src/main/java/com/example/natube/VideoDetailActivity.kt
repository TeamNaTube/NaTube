package com.example.natube

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.natube.databinding.ActivityVideoDetailBinding
import com.example.natube.editprofile.LikedItemPreferencesManager
import com.example.natube.model.UnifiedItem

class VideoDetailActivity : AppCompatActivity() {


    // 뷰 바인딩 및 변수 초기화
    private lateinit var binding: ActivityVideoDetailBinding
//    private lateinit var mContext: Context

    // 뷰 모델
    private val viewModel: VideoDetailActivityViewModel by viewModels()

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

            when (it.isLike) {
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
        binding.ibActivityDetailBtnShare.setOnClickListener {
            Toast.makeText(this@VideoDetailActivity, "링크가 클립보드에 저장되었습니다.", Toast.LENGTH_SHORT).show()
            // 시스템 클립보드 가져오기
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 새로운 ClipData 객체로 데이터 복사하기
            val clip: ClipData = ClipData.newPlainText("url", "${itemDetail.thumbnailsUrl}")
            // Set the clipboard's primary clip.
            clipboard.setPrimaryClip(clip)

        }
    }

    private fun setLikeButton() {
        binding.ibActivityDetailBtnLike.setOnClickListener {
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
        binding.btnActivityDetailBack.setOnClickListener {
            intentLike(itemDetail)
            Log.d(
                "happyVideoDetail",
                "^^ ${itemDetail.isLike} right before closing activity (intenting)"
            )

        }
    }

    private fun intentLike(item: UnifiedItem?) {
//        val itemIntent = Intent(this, MainActivity::class.java)
//        itemIntent.putExtra("selected item", item)
//        setResult(RESULT_OK, itemIntent)
        Log.d("happyvideoDetail","흠 좀 이상")
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