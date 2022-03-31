package com.blogspot.svdevs.expensetracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceControl
import androidx.core.content.ContextCompat
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.databinding.ActivityIncomeBinding
import com.blogspot.svdevs.expensetracker.model.Transaction

class IncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeBinding

    private lateinit var list: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.dark_bg)

       list = MainActivity.incomeTranscations

        binding.textView.text = list[1].title


    }
}