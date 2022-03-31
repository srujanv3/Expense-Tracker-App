package com.blogspot.svdevs.expensetracker.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.adapter.TransactionAdapter
import com.blogspot.svdevs.expensetracker.databinding.ActivityMainBinding
import com.blogspot.svdevs.expensetracker.model.Transaction
import com.blogspot.svdevs.expensetracker.viewmodel.TransactionViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var transactions: List<Transaction>
    private lateinit var oldTransactions: List<Transaction>
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var deleteTransaction: Transaction

    companion object {
         lateinit var incomeTranscations: ArrayList<Transaction>
         lateinit var expenseTransactions: ArrayList<Transaction>

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.dark_bg)

        transactions = arrayListOf()
        incomeTranscations = arrayListOf()
        expenseTransactions = arrayListOf()

        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this@MainActivity)

        // init view model
        transactionViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TransactionViewModel::class.java)

        transactionViewModel.allTransactions.observe(this, Observer { transactionsList ->
            transactionsList?.let {
                transactions = it // handles the index out of bounds exception
                transactionAdapter.updateList(it)
                updateDashBoard()
            }
        })

        binding.incomeCard.setOnClickListener {
           sendIntentIncome()
        }

        binding.expenseCard.setOnClickListener {
            sendIntentExpense()
        }

        binding.rvTransactions.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = transactionAdapter
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }

        // swipe to delete feature
        val itemTouchListener = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }
        val swipeHelper = ItemTouchHelper(itemTouchListener)
        swipeHelper.attachToRecyclerView(binding.rvTransactions)

    }

    private fun sendIntentExpense() {
        expenseTransactions = transactions.filter { it.amount < 0 } as ArrayList<Transaction>
        val intent = Intent(this,ExpenseActivity::class.java)
        //intent.putExtra("EXP",expenseTransactions)
        startActivity(intent)
    }

    private fun sendIntentIncome() {
        incomeTranscations = transactions.filter { it.amount > 0 } as ArrayList<Transaction>
        val intent = Intent(this, IncomeActivity::class.java)
       // intent.putParcelableArrayListExtra("INC",incomeTranscations as java.util.ArrayList<out Parcelable>)
        startActivity(intent)
    }

    private fun deleteTransaction(transaction: Transaction) {
        deleteTransaction = transaction
        oldTransactions = transactions

        transactionViewModel.deleteTransaction(transaction)
        transactions = transactions.filter { it.id != transaction.id }

        updateDashBoard()
        transactionAdapter.updateList(transactions)
        showSnackBar()
    }

    private fun showSnackBar() {
        val snackbar =
            Snackbar.make(binding.coordinatorLayout, "Transaction deleted...", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            undoDelete()
        }
            .setBackgroundTint(ContextCompat.getColor(this, R.color.dark_bg_one))
            .setActionTextColor(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }

    private fun undoDelete() {
        transactionViewModel.insertTransaction(deleteTransaction)
        transactions = oldTransactions
        transactionAdapter.updateList(transactions)
        updateDashBoard()
    }

    @SuppressLint("SetTextI18n")
    fun updateDashBoard() {

        val totalAmount = transactions.map { it.amount }.sum()
        val earnedAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val expenseAmount = totalAmount - earnedAmount

        Log.d("TOTAL", "updateDashBoard: $totalAmount")

        binding.apply {
            tvBalance.text = "Rs.%.2f".format(totalAmount)
            tvIncome.text = "Rs.%.2f".format(earnedAmount)
            tvExpense.text = "Rs.%.2f".format(expenseAmount)
        }
    }
}
