package com.blogspot.svdevs.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blogspot.svdevs.expensetracker.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM all_transactions")
    fun getAllTransactions(): LiveData<List<Transaction>>
}