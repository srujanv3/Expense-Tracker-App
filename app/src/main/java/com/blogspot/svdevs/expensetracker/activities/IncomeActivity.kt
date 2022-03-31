package com.blogspot.svdevs.expensetracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.adapter.TransactionAdapter
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

        binding.rvIncome.apply {
            layoutManager = LinearLayoutManager(this@IncomeActivity)
            adapter = TransactionAdapter(list)
            setHasFixedSize(true)
        }
    }
}