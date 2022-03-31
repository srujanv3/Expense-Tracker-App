package com.blogspot.svdevs.expensetracker.repo

import androidx.lifecycle.LiveData
import com.blogspot.svdevs.expensetracker.db.AppDatabase
import com.blogspot.svdevs.expensetracker.db.TransactionDao
import com.blogspot.svdevs.expensetracker.model.Transaction

class TransactionRepo(private val dao: TransactionDao) {

    val allTransactions : LiveData<List<Transaction>> = dao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) = dao.insert(transaction)

    suspend fun updateTransaction(transaction: Transaction) = dao.update(transaction)

    suspend fun deleteTransaction(transaction: Transaction) = dao.delete(transaction)

}