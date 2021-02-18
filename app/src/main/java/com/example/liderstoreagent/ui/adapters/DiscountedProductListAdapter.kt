package com.example.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import kotlinx.android.synthetic.main.discounted_item_product.view.*

class DiscountedProductListAdapter :
    ListAdapter<DiscountedProduct, DiscountedProductListAdapter.ViewHolder>(DiffItem) {

    object DiffItem : DiffUtil.ItemCallback<DiscountedProduct>() {
        override fun areItemsTheSame(
            oldItem: DiscountedProduct,
            newItem: DiscountedProduct
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DiscountedProduct,
            newItem: DiscountedProduct
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.unit == newItem.unit &&
                    oldItem.provider == newItem.provider
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.discounted_item_product, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(d: DiscountedProduct) {
            itemView.apply {
                discountedProductName.text = d.name
                discountedProductQuantity.text = d.quantity + " " + d.unit
                discountedProductProvider.text = d.provider
            }
        }
    }

}