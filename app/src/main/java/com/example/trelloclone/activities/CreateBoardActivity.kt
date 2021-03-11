package com.example.trelloclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trelloclone.R
import kotlinx.android.synthetic.main.activity_create_board.*

class CreateBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)

        setUpActionBar()
    }

    private fun setUpActionBar(){
        setSupportActionBar(toolbar_create_board_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.setTitle(R.string.create_board_title)
        }
        toolbar_create_board_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}