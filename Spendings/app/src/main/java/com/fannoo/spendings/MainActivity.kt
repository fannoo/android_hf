package com.fannoo.spendings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fannoo.spendings.databinding.ActivityMainBinding
import com.fannoo.spendings.fragments.AddFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}