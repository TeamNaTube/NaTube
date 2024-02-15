package com.example.natube

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.natube.databinding.ActivityMainBinding
import com.example.natube.editprofile.LikedItemPreferencesManager
import com.example.natube.model.UnifiedItem
import com.example.natube.ui.home.HomeRepository
import com.example.natube.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val appData by lazy { AppData(application) }
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMainActivty)

        navController = findNavController(R.id.main_frame_layout)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        binding.navView.setupWithNavController(menu!!, navController)
        return true
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
    override fun onResume() {
        super.onResume()

        MyChannelPreferencesManager.with(this)
        LikedItemPreferencesManager.with(this)
    }

    private fun initViewModel() {
        val factory = ViewModelFactory(HomeRepository(appData))
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

}