package com.blogspot.svdevs.expensetracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.databinding.ActivityAddTransactionBinding
import com.blogspot.svdevs.expensetracker.model.Transaction
import com.blogspot.svdevs.expensetracker.viewmodel.TransactionViewModel

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    private lateinit var title: String
    private lateinit var desc: String
    private var amount: Double? = null

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.dark_bg)

        binding.apply {
            btnAddTransaction.setOnClickListener {
                addTransactionIfValid()
            }
            ivClose.setOnClickListener {
                finish()
            }
        }

        modifyState()

        //init view model
        transactionViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TransactionViewModel::class.java)

    }

    private fun modifyState() {
        binding.titleInputValue.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.labelInput.error = null
            }
        }

        binding.amountInputValue.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.amountInput.error = null
            }
        }
    }

    private fun addTransactionIfValid() {

        binding.apply {
            title = titleInputValue.text.toString()
            amount = amountInputValue.text.toString().toDoubleOrNull()
            desc = descriptionInput.text.toString()
        }
        if (title.isEmpty()) {
            binding.labelInput.error = "Please enter valid title"
        }
        else if (amount == null) {
            binding.amountInput.error = "Please enter valid amount"
        }else if (title.isNotEmpty() && amount != null){
            transactionViewModel.insertTransaction(Transaction(title,amount!!,desc,0))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}