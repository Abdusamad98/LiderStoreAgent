package com.example.liderstoreagent.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.getproduct.Discount
import com.example.liderstoreagent.ui.adapters.DiscountsAdapter
import kotlinx.android.synthetic.main.dialog_discounts.*

class DiscountsDialog : DialogFragment() {
    var lists : List<Discount> = ArrayList()
    private val adapter by lazy { DiscountsAdapter(lists) }
    private var listener : ((Int) -> Unit)?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_discounts,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listDiscount.adapter = adapter
        listDiscount.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnDiscountChosen {
            lists[it].isChecked = !lists[it].isChecked
            adapter.notifyItemChanged(it)
        }

        discountButton.setOnClickListener {
            var discount = 0
            lists.filter { it.isChecked }.forEach {
                discount += it.discount
            }
            listener?.invoke(discount)
            dismiss()
        }
    }

    fun clickListener(f : (Int) -> Unit) {
        listener = f
    }
}