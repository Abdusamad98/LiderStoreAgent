package com.example.liderstoreagent.ui.dialogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.categorymodel.CategoryData
import com.example.liderstoreagent.ui.adapters.CategoryListAdapter
import kotlinx.android.synthetic.main.category_dialog.view.*


class CategoryChooseDialog(context: Context, categories: List<CategoryData>) :
    BaseDialog(context, R.layout.category_dialog) {
    private var listener: ((Int) -> Unit)? = null
    private var adapter = CategoryListAdapter(categories)


    init {
        view.apply {

            recyclerCategories.adapter = adapter
            recyclerCategories.layoutManager = LinearLayoutManager(context)

            adapter.setOnCategoryChosen { data ->
                listener?.invoke(data)
                close()
            }
        }
    }

    fun setOnCategoryChosen(f: ((Int) -> Unit)?) {
        listener = f
    }
}

