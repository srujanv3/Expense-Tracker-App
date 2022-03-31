package com.blogspot.svdevs.expensetracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.adapter.TransactionAdapter
import com.blogspot.svdevs.expensetracker.databinding.ActivityExpenseBinding
import com.blogspot.svdevs.expensetracker.model.Transaction

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding

    private lateinit var list: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.dark_bg)

        list = MainActivity.expenseTransactions

        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@ExpenseActivity)
            adapter = TransactionAdapter(list)
            setHasFixedSize(true)
        }
    }
}