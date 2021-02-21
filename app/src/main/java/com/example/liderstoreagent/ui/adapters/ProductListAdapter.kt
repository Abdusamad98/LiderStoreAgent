package com.example.liderstoreagent.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.productsmodel.ProductData
import com.example.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_products.view.*

class ProductListAdapter : ListAdapter<ProductData, ProductListAdapter.ViewHolder>(DiffItem) {

    var query = ""

    private var clickListener : ((Int,String,String) -> Unit)? = null
    object DiffItem : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.product_type == newItem.product_type &&
                    oldItem.unit == newItem.unit
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {
                productSell.setOnClickListener {
                    clickListener?.invoke(getItem(adapterPosition).id,getItem((adapterPosition)).name,getItem((adapterPosition)).unit)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(d: ProductData) {

            itemView.apply {
                if (query != "") productName.text = d.name spannableText query
                else productName.text = d.name

                productDate.text = d.last_update.substring(0,10)
                productProvider.text = d.provider

                if (d.product_type.equals("limited")) {
                    productQuantity.text = d.quantity +" "+ d.unit
                    productStatus.text = "Vip emas"
                    productStatus.setTextColor(Color.RED)
                } else if (d.product_type.equals("unlimited")) {
                    quantityLinear.visibility = View.GONE
                    productStatus.text = "Vip"
                }

            }
        }
    }

    fun clickedProduct(f : (Int, String, String)->Unit) {
        clickListener = f
    }

}