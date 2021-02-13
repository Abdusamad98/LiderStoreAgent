package com.example.liderstoreagent.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.ClientsData
import kotlinx.android.synthetic.main.item_client.view.*

class ClientListAdapter : ListAdapter<ClientsData, ClientListAdapter.ViewHolder>(DiffItem) {


    object DiffItem : DiffUtil.ItemCallback<ClientsData>() {
        override fun areItemsTheSame(oldItem: ClientsData, newItem: ClientsData): Boolean {
            return oldItem.client_name == newItem.client_name
        }

        override fun areContentsTheSame(oldItem: ClientsData, newItem: ClientsData): Boolean {
            return oldItem.client_name == newItem.client_name &&
                    oldItem.client_address == newItem.client_address &&
                    oldItem.debt == newItem.debt &&
                    oldItem.responsible_agent == newItem.responsible_agent &&
                    oldItem.phone == newItem.phone
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
    )

    private var listener_sell: ((Int) -> Unit)? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

                sellProduct.setOnClickListener {
                    listener_sell?.invoke(adapterPosition)
                }
//                deleting.setOnClickListener {
//                    listener_delete?.invoke(adapterPosition)
//                }

            }
        }


        fun bind(d: ClientsData) {

            itemView.apply {
                client_name.text = d.client_name
                client_address.text = d.client_address
                responsible_agent.text = d.responsible_agent
                phone.text = d.phone
                debt.text = d.debt

                if (d.debt.toInt() > 0) {
                    debt.setTextColor(Color.RED)
                } else debt.setTextColor(Color.GREEN)


            }
        }
    }

    fun setOnSellProductListener(f: ((Int) -> Unit)?) {
        listener_sell = f
    }

//
//    fun setOnDeleteListener(f: ((Int) -> Unit)?) {
//        listener_delete = f
//    }
}