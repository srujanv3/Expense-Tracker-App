package com.blogspot.svdevs.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blogspot.svdevs.expensetracker.db.AppDatabase
import com.blogspot.svdevs.expensetracker.model.Transaction
import com.blogspot.svdevs.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TransactionViewModel(application:Application): AndroidViewModel(application) {

    val repository: TransactionRepo
    val allTransactions:LiveData<List<Transaction>>

    init {
        val dao = AppDatabase.getDatabase(application).getTransactionDao()

        allTransactions = dao.getAllTransactions()
        repository = TransactionRepo(dao)
    }
    fun insertTransaction(transaction: Transaction ) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction:Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTransaction(transaction)
    }
}