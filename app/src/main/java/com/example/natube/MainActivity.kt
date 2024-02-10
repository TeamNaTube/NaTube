package com.example.natube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.natube.databinding.ActivityMainBinding
import com.example.natube.model.UnifiedItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_my_video
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initView()
    }


    override fun onStart() {
        super.onStart()
        Log.d("happyMainOnStart?", "^^ 오예 *")
        updateLike()
    }

    private fun initView() {

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
}