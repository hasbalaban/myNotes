package com.example.mynotes.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.mynotes.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //      mainActivityDataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)


        val navController = findNavController(R.id.fragment2)

        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    //when you navigate in appliciation with support of navigation ,show upActionBar in top
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment2)
        return navController.navigateUp()
    }

    // when application on pause clear your private password and set it as a null.
    override fun onPause() {
        Toast.makeText(this, "durdu", Toast.LENGTH_SHORT).show()
        Ozelsifre = null
        super.onPause()
    }


}