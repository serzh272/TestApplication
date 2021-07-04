package ru.serzh272.testapplication.ui.adapters

import android.graphics.Color
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.serzh272.testapplication.App
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.data.Payment
import ru.serzh272.testapplication.databinding.ItemPaymentBinding
import java.text.DecimalFormat

class PaymentsAdapter:RecyclerView.Adapter<PaymentsAdapter.PaymentItemViewHolder>() {
    private lateinit var itemBindig: ItemPaymentBinding
    var items = listOf<Payment>()
    class PaymentItemViewHolder(private val binding: ItemPaymentBinding):  RecyclerView.ViewHolder(binding.root){
        fun bind(item:Payment){
            val strAmount = String.format("%s ${item.currency}", item.amount)
            val spanAmount = strAmount.toSpannable()
            spanAmount.setSpan(ForegroundColorSpan(App.applicationContext().getColor(R.color.purple_200)),strAmount.length - item.currency.length, strAmount.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spanAmount.setSpan(object :MetricAffectingSpan(){
                override fun updateDrawState(tp: TextPaint?) {
                    with(tp!!){
                        textSize *= 0.5f
                        isFakeBoldText = true
                    }
                }

                override fun updateMeasureState(textPaint: TextPaint) {
                    with(textPaint){
                        textSize *= 0.5f
                        isFakeBoldText = true
                    }
                }

            },strAmount.length - item.currency.length, strAmount.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvAmount.text = spanAmount
            binding.tvCurrency.initials = item.currency
            binding.tvDesc.text = item.desc
            binding.tvCreated.text = item.getDateCreated()
        }
    }
    fun updateData(data:List<Payment>){
        val diffCallback =object :DiffUtil.Callback(){
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].hashCode() == data[newItemPosition].hashCode()
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].amount == data[newItemPosition].amount &&
                        items[oldItemPosition].created == data[newItemPosition].created &&
                        items[oldItemPosition].currency == data[newItemPosition].currency &&
                        items[oldItemPosition].desc == data[newItemPosition].desc
            }

        }
        val diffRes = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffRes.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentItemViewHolder {
        itemBindig = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PaymentItemViewHolder(itemBindig)
    }

    override fun onBindViewHolder(holder: PaymentItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}