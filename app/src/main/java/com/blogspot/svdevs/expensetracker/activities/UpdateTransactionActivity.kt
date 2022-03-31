package com.blogspot.svdevs.expensetracker.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.databinding.ActivityUpdateTransactionBinding
import com.blogspot.svdevs.expensetracker.db.AppDatabase
import com.blogspot.svdevs.expensetracker.model.Transaction
import com.blogspot.svdevs.expensetracker.viewmodel.TransactionViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTransactionBinding
    private lateinit var title: String
    private lateinit var desc: String
    private var amount: Double? = null

    private lateinit var transaction: Transaction
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.dark_bg)

        transaction = intent.getSerializableExtra("transaction") as Transaction

        //init view model
        transactionViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TransactionViewModel::class.java)

        binding.apply {
            titleInputValue.setText(transaction.title)
            amountInputValue.setText(transaction.amount.toString())
            descriptionInput.setText(transaction.tag)
        }

        binding.apply {
            btnUpdateTransaction.setOnClickListener {
                addTransactionIfValid()
            }
            ivClose.setOnClickListener {
                finish()
            }
        }

        modifyState()

        binding.rootView.setOnClickListener {
            this.window.decorView.clearFocus()
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(it.windowToken,0)
        }
    }

    private fun modifyState() {
        binding.titleInputValue.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
            if (it!!.count() > 0) {
                binding.labelInput.error = null
            }
        }

        binding.amountInputValue.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
            if (it!!.count() > 0) {
                binding.amountInput.error = null
            }
        }

        binding.descriptionInput.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
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
        }else {
            transactionViewModel.updateTransaction(Transaction(title, amount!!,desc,transaction.id))
            finish()

        }
    }

    // just for reference... not used in the project
    private fun update(transaction: Transaction) {

        val db = Room.databaseBuilder(this, AppDatabase::class.java,"transactions").build()

        GlobalScope.launch {

            db.getTransactionDao().update(transaction)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}