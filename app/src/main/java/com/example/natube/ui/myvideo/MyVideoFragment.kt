package com.example.natube.ui.myvideo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.natube.editprofile.EditChannelActivity
import com.example.natube.editprofile.LikedItemPreferencesManager
import com.example.natube.MyChannelPreferencesManager
import com.example.natube.R
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentMyVideosBinding
import com.example.natube.model.UnifiedItem
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class MyVideoFragment : Fragment() {

    private var _binding: FragmentMyVideosBinding? = null
    private val myVideoViewModel: MyVideoViewModel by viewModels()
    private val myVideoAdapter: MyVideoAdapter by lazy { MyVideoAdapter(myVideoViewModel) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val gsonBuilder = GsonBuilder()

    private var likedItems = LikedItemPreferencesManager.getAll<UnifiedItem>()
    private var myInfo = MyChannelPreferencesManager.getAll<MyChannel>()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("HappyMyVideo", "^^ from sharedpref $likedItems")

        _binding = FragmentMyVideosBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        Log.d("happymyvid", " 온 크 리 에 이 트 뷰")
        initViewModel()

    }

    private fun initViewModel() {
        with(myVideoViewModel) {
            selectedItem.observe(viewLifecycleOwner) {

                when (it) {
                    null -> Unit
                    else -> {
                        val detailIntent = Intent(activity, VideoDetailActivity::class.java)
                        detailIntent.putExtra("selected item", it)
                        startActivity(detailIntent)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        likedItems = LikedItemPreferencesManager.getAll()
        myVideoAdapter.submitList(likedItems)
        myInfo = MyChannelPreferencesManager.getAll()
        onResumeView()

    }

    private fun onResumeView() {
        Log.d("happymyvid", " 온 리 쥼 뷰")
        setView()

    }

    private fun initView() {


        setViewModelValues()
        setMyVideoAdapter()
        setListeners()


    }

    private fun setView() {
        myInfo = MyChannelPreferencesManager.getAll()
        Log.d("myInfo","$myInfo")
        when (myInfo.size) {
            0 -> {
                setMyProfileDialog()
                Log.d("happymyVid", "** 다이얼로그 창 열리기 전 or 후?")
            }
            else -> {

                setMyProfile()
            }
        }
    }

    private fun setMyProfile() {


        with(binding) {
            tvActivityEditChannelUsername.text = myInfo[0]?.myChannelName
            tvActivityEditChannelUserDescription.text = myInfo[0]?.myChannelDescription
            if (myInfo[0]?.myProfilePicture == null) ivFragmentMyVideoProfileImage.setImageURI(Uri.parse(myInfo[0]?.myProfilePicture)) else ivFragmentMyVideoProfileImage.setImageResource(R.drawable.img_empty_profile_picture)

            if (myInfo[0]?.myBackgroundPicture == null) ivFragmentMyVideoImgBackground.setImageURI(Uri.parse(myInfo[0]?.myBackgroundPicture)) else ivFragmentMyVideoImgBackground.setImageResource(R.drawable.img_thumbnail)


        }
    }

    private fun setMyProfileDialog() {
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("내 채널 설정")
        builder.setMessage("내가 좋아요한 영상들 리스트를 보려면 내 채널 추가를 해야합니다.")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setCancelable(false)

        // 버튼 클릭시에 무슨 작업을 할 것인가!
        val listener = DialogInterface.OnClickListener { p0, p1 ->
            when (p1) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val addIntent = Intent(activity, EditChannelActivity::class.java)
                    startActivity(addIntent)
                }
            }
        }

        builder.setPositiveButton("내 채널 추가", listener)

        builder.show()
    }

    private fun setListeners() {
        binding.ibtnFragmentMyVideoEdit.setOnClickListener {
            val myInfo = MyChannel(
                binding.tvActivityEditChannelUsername.text.toString(),
                null,
                null,
                binding.tvActivityEditChannelUserDescription.text.toString()
            )
            val editIntent = Intent(activity, EditChannelActivity::class.java)
            startActivity(editIntent)
        }
    }

    private fun setViewModelValues() {
        myVideoViewModel.getSelectedItem(null)
    }

    private fun setMyVideoAdapter() {

        binding.rvFragmentMyVideoFavourites.adapter = myVideoAdapter

        Log.d("happyMyVideoFragment TagRV", "^^get SHaredPref ? ${likedItems.size}")
        myVideoAdapter.submitList(likedItems)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}