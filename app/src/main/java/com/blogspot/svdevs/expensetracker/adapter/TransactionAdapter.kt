package com.blogspot.svdevs.expensetracker.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.svdevs.expensetracker.R
import com.blogspot.svdevs.expensetracker.activities.ExpenseActivity
import com.blogspot.svdevs.expensetracker.activities.IncomeActivity
import com.blogspot.svdevs.expensetracker.activities.UpdateTransactionActivity
import com.blogspot.svdevs.expensetracker.databinding.ItemLayoutBinding
import com.blogspot.svdevs.expensetracker.model.Transaction
import java.io.Serializable
import kotlin.math.abs

class TransactionAdapter(private var list: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        val context = holder.itemView.context
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateTransactionActivity::class.java)
            intent.putExtra("transaction",currentItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Transaction>) {
        this.list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {

            val incomeList = ArrayList<Transaction>()
            val expenseList = ArrayList<Transaction>()

            binding.apply {
                tvItemTitle.text = transaction.title
                val context = tvItemAmount.context

                if (transaction.amount >= 0) {
                    tvItemAmount.text = "+ Rs.%.2f".format(transaction.amount)
                    tvItemAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                    incomeList.add(transaction)
                    val intent = Intent(context,IncomeActivity::class.java)
                    intent.putExtra("INC",transaction)
//                    intent.putParcelableArrayListExtra("ILIST",incomeList)
//                    sendIntent(incomeList)

                }else {
                    tvItemAmount.text = "- Rs.%.2f".format(abs(transaction.amount))
                    tvItemAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                    expenseList.add(transaction)
                    val intent = Intent(context, ExpenseActivity::class.java)
//                    intent.putParcelableArrayListExtra("ELIST",expenseList as Serializable)
//                    sendIntent(expenseList)
                }
            }

        }

        private fun sendIntent(list: ArrayList<Transaction>) {
            Intent().putExtra("LIST",list as Serializable)
        }

    }
}