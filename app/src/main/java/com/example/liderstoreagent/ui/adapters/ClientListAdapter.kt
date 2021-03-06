package com.example.liderstoreagent.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.utils.spannableText
import kotlinx.android.synthetic.main.item_client.view.*
import kotlinx.android.synthetic.main.item_products.view.*

class ClientListAdapter : ListAdapter<com.example.liderstoreagent.data.models.clientmodel.ClientsData, ClientListAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<com.example.liderstoreagent.data.models.clientmodel.ClientsData>() {
        override fun areItemsTheSame(oldItem: com.example.liderstoreagent.data.models.clientmodel.ClientsData, newItem: com.example.liderstoreagent.data.models.clientmodel.ClientsData): Boolean {
            return oldItem.client.id == newItem.client.id
        }

        override fun areContentsTheSame(oldItem: com.example.liderstoreagent.data.models.clientmodel.ClientsData, newItem: com.example.liderstoreagent.data.models.clientmodel.ClientsData): Boolean {
            return oldItem.client.name == newItem.client.name &&
                    oldItem.client.responsible_agent == newItem.client.responsible_agent &&
                    oldItem.client.phone == newItem.client.phone  &&
                    oldItem.client.address == newItem.client.address &&
                    oldItem.total_debt == newItem.total_debt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: com.example.liderstoreagent.data.models.clientmodel.ClientsData) {

            itemView.apply {
                if (query != "") client_name.text = d.client.name spannableText query
                else client_name.text = d.client.name

                client_address.text = d.client.address
                responsible_agent.text = d.client.responsible_agent
                phone.text = d.client.phone
                debt.text = d.total_debt.toString()

                if (d.total_debt > 0) {
                    debt.setTextColor(Color.RED)
                } else debt.setTextColor(Color.GREEN)

            }
        }
    }


}