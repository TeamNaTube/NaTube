package com.example.natube.editprofile

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.natube.MainActivity
import com.example.natube.MyChannelPreferencesManager
import com.example.natube.R
import com.example.natube.databinding.ActivityEditChannelBinding
import com.example.natube.ui.myvideo.MyChannel
import com.example.natube.ui.myvideo.MyVideoFragment

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
    private var description: String? = null
    private var profileUri: Uri? = null
    private var backgroundUri: Uri? = null

    // 이미지 업로드 관련 변수
    private var profileResId: Int? = null
    private var profilePath: String? = null
    private lateinit var groupText: String
    private lateinit var buttonType: String
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
            myInfo = MyChannel(name,profileUri.toString(),backgroundUri.toString(),description)
            when (allInfo.size) {
                0 -> {
                    MyChannelPreferencesManager.put(myInfo, myInfo.myChannelName!!)
                    Log.d("happyedit","^^ ADD: my profile details are added")
                    Log.d("happyeditsharedpref", "^^ SP ${MyChannelPreferencesManager.myChannelPreferences.all}")

                }
                else -> {
                    MyChannelPreferencesManager.remove(allInfo[0], allInfo[0]?.myChannelName!!)
                    MyChannelPreferencesManager.put(myInfo, myInfo.myChannelName!!)
                    Log.d("happyedit","^^ UPDATE: my profile details are updated")
                }
            }
            val intent = Intent(this, MyVideoFragment::class.java)
            intent.putExtra("profile uri",profileUri)
            intent.putExtra("background uri", backgroundUri)
            setResult(RESULT_OK, intent)

            finish()
        }
    }

    private fun initView() {
        binding.btnActivityEditChannelComplete.isEnabled = false
        MyChannelPreferencesManager.with(this)
        getMyChannelInfo()
        setListener()

    }

    private fun setListener() {

        //LikedItemPreferencesManager.put(it, it.thumbnailsUrl)

        with(binding) {

            // editText Listeners
            etActivityEditChannelChannelName.addTextChangedListener {
                name = it.toString()
                editViewModel.validateName(it.toString())
            }
            etActivityEditChannelChannelDescription.addTextChangedListener{
                description = it.toString()
            }

            // image button listeners
            ivActivityEditChannelProfileImage.setOnClickListener {
                Log.d("happyEdit", "^^ Profile clicked")
                buttonType = "profile"
                val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(this@EditChannelActivity, permission)
                    != PackageManager.PERMISSION_GRANTED
                ) requestPermissionLauncher.launch(permission)
                else openGallery()
            }
            ivActivityEditChannelImgBackground.setOnClickListener {
                Log.d("happyEdit", "^^ Background Clicked")
                buttonType = "background"
                val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(this@EditChannelActivity, permission)
                    != PackageManager.PERMISSION_GRANTED
                ) requestPermissionLauncher.launch(permission)
                else openGallery()
            }



        }
    }
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openGallery()
            else Log.e("myLogTag", "RequestPermission not granted")
        }

    // 가져온 사진 이미지뷰에 세팅
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) result.data?.data?.let {
                profileResId = null
                profilePath = RealPathUtil.getRealPathFromURI_API19(this, it)
                when (buttonType) {
                    "profile" -> {
                        profileUri = it
                        binding.ivActivityEditChannelProfileImage.setImageURI(it)
                    }
                    "background" -> {
                        backgroundUri = it
                        binding.ivActivityEditChannelImgBackground.setImageURI(it)
                    }
                }

            } else {
                Log.e("myLogTag", "result.resultCode != RESULT_OK")
                Toast.makeText(this, "설정 -> 앱 에서 이미지 설정 권한을 추가해 주세요.", Toast.LENGTH_SHORT).show()
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



    private fun openGallery() {
        /*
        ACTION_PICK 으로 하면 사진 선택하게끔 나오는데 절대경로 오류남.
        ACTION_OPEN_DOCUMENT으로 해야 가능. 맘에 안드네..
         */
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.putExtra("Button Type", buttonType)
        pickImageLauncher.launch(intent)
    }
}