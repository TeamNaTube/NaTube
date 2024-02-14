package com.example.natube

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.natube.databinding.ActivityMainBinding
import com.example.natube.editprofile.LikedItemPreferencesManager
import com.example.natube.model.UnifiedItem
import com.example.natube.ui.home.HomeFragment
import com.example.natube.ui.home.HomeRepository
import com.example.natube.ui.home.HomeViewModel
import com.example.natube.ui.myvideo.MyVideoFragment
import com.example.natube.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val appData by lazy{ AppData(application) }
    private lateinit var homeViewModel: HomeViewModel

    // fragments
    private val TAG_SEARCH = "search_fragment"
    private val TAG_HOME = "home_fragment"
    private val TAG_MY_VIDEO = "my_video_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, HomeFragment())

        binding.navView.setOnItemSelectedListener { item ->
            Log.d("happymain", "item Clicked왜 실행이 안되는 거지...")
            when(item.itemId) {
                R.id.home_fragment -> {
                    setFragment(TAG_HOME, HomeFragment())
                    Log.d("happymain", "home 왜 실행이 안되는 거지...")
                }
                R.id.search_fragment -> {
                    setFragment(TAG_SEARCH, SearchFragment())
                    Log.d("happymain", "search왜 실행이 안되는 거지...")
                }
                else -> {
                    setFragment(TAG_MY_VIDEO, MyVideoFragment())
                    Log.d("happymain", "my video왜 실행이 안되는 거지...")
                }
            }
            true
        }

//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_search, R.id.navigation_my_video
//            )
//        )
//
//        navView.setupWithNavController(navController)
//
//        val host = NavHostFragment.create(R.navigation.mobile_navigation)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment_activity_main, host)
//            .setPrimaryNavigationFragment(host)
//            .commit()
        initView()
    }


    override fun onStart() {
        super.onStart()
        Log.d("happyMainOnStart?", "^^ 오예 *")
        updateLike()
    }

    private fun initView() {
        MyChannelPreferencesManager.with(this)
        LikedItemPreferencesManager.with(this)
    }

    override fun onResume() {
        super.onResume()

        MyChannelPreferencesManager.with(this)
        LikedItemPreferencesManager.with(this)
    }

    private fun updateLike() {

        Log.d("happyMainActivity", "^^ 여기 실행 됨? ${intent.hasExtra("selected item")}")

        if (intent.hasExtra("item to main")) {
            val itemDetail = intent.getParcelableExtra<UnifiedItem>("item to main")!!
            Log.d("happyMainActivity", "^^ ${itemDetail.isLike}")

        }
    }

//    private fun initViewModel() {
//        // getting selected items in either category rv or keyword rv
//        selectedItem.observe(viewLifecycleOwner){
//
//            val intent = Intent(activity, VideoDetailActivity::class.java).apply {
//                putExtra("selected item", it)
//            }
//            activity?.setResult(RESULT_OK, intent)
//            if (activity?.isFinishing == false) activity?.finish()
//
//
//        }
//    }

    private fun initViewModel(){
        val factory = ViewModelFactory(HomeRepository(appData))
        homeViewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.main_frame_layout, fragment, tag)
        }

        val search = manager.findFragmentByTag(TAG_SEARCH)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myVideo = manager.findFragmentByTag(TAG_MY_VIDEO)

        if (search != null){
            fragTransaction.hide(search)
        }

        if (home != null){
            fragTransaction.hide(home)
        }

        if (myVideo != null) {
            fragTransaction.hide(myVideo)
        }

        if (tag == TAG_SEARCH) {
            if (search!=null){
                fragTransaction.show(search)
            }
        }
        else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_MY_VIDEO){
            if (myVideo != null){
                fragTransaction.show(myVideo)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}