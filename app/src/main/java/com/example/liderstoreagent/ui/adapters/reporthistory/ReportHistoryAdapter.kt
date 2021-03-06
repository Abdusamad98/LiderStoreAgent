package com.example.liderstoreagent.ui.adapters.reporthistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import com.example.liderstoreagent.utils.Constants
import kotlinx.android.synthetic.main.item_report_hisyory.view.*

class ReportHistoryAdapter :
    ListAdapter<ReportHistory, ReportHistoryAdapter.ViewHolder>(DiffItem) {

    var query = ""

    object DiffItem : DiffUtil.ItemCallback<ReportHistory>() {
        override fun areItemsTheSame(
            oldItem: ReportHistory,
            newItem: ReportHistory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReportHistory,
            newItem: ReportHistory
        ): Boolean {
            return oldItem.comment == newItem.comment &&
                    oldItem.image == newItem.image
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_hisyory, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.apply {

            }
        }

        fun bind(d: ReportHistory) {
            itemView.apply {
                commentText.text = d.comment
                reportDate.text = d.created_date.substring(0, 10) +" "+ d.created_date.substring(11, 16)
                if (d.image != null) {
                    Glide.with(reportImage.context).load(Constants.BASE_URL + d.image)
                        .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                        .into(reportImage)
                }
            }
        }
    }


}
