package com.example.natube.ui.myvideo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.natube.editprofile.EditChannelActivity
import com.example.natube.editprofile.LikedItemPreferencesManager
import com.example.natube.MyChannelPreferencesManager
import com.example.natube.R
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentMyVideosBinding
import com.example.natube.model.UnifiedItem

class MyVideoFragment : Fragment() {

    private var _binding: FragmentMyVideosBinding? = null
    private val myVideoViewModel: MyVideoViewModel by viewModels()
    private val myVideoAdapter: MyVideoAdapter by lazy { MyVideoAdapter(myVideoViewModel) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val likedItems = LikedItemPreferencesManager.getAll<UnifiedItem>()
    private var myInfo = MyChannelPreferencesManager.getAll<MyChannel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("HappyMyVideo", "^^ from sharedpref $likedItems")
        val myVideoViewModel =
            ViewModelProvider(this)[MyVideoViewModel::class.java]

        _binding = FragmentMyVideosBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
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
        val likedItems = LikedItemPreferencesManager.getAll<UnifiedItem>()
        myVideoAdapter.submitList(likedItems)
        myInfo = MyChannelPreferencesManager.getAll()
        initView()
        initViewModel()
    }

    private fun onResumeView() {
        setView()

    }

    private fun initView() {


        setView()
        setViewModelValues()
        setMyVideoAdapter()
        setListeners()
    }

    private fun setView() {
        when (myInfo.size) {
            0 -> setMyProfileDialog()
            else -> setMyProfile()
        }
    }

    private fun setMyProfile() {
        with(binding) {
            tvActivityEditChannelUsername.text = myInfo[0]?.myChannelName
            tvActivityEditChannelUserDescription.text = myInfo[0]?.myChannelDescription
            ivActivityEditChannelProfileImage.setImageURI(myInfo[0]?.myProfilePicture)
            ivActivityEditChannelImgBackground.setImageURI(myInfo[0]?.myBackgroundPicture)
        }
    }

    private fun setMyProfileDialog() {
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("내 채널 설정")
        builder.setMessage("내가 좋아요한 영상들 리스트를 보려면 내 채널 추가를 해야합니다.")
        builder.setIcon(R.mipmap.ic_launcher)

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
            editIntent.putExtra("my Channel Info", myInfo)
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