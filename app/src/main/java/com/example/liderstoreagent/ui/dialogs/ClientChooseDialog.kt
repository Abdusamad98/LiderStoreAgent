package com.example.liderstoreagent.ui.dialogs

import android.content.Context
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.ui.adapters.ClientSearchListAdapter
import kotlinx.android.synthetic.main.client_choose_dialog.view.*


@Suppress("DEPRECATION")
class ClientChooseDialog(context: Context, clients: List<ClientsData>) :
    BaseDialog(context, R.layout.client_choose_dialog) {

    private var querySt = ""
    private var listener: ((Int, String,Int) -> Unit)? = null
    private var adapter = ClientSearchListAdapter()



    init {
        view.apply {

            adapter.submitList(clients)
            recyclerClientSearch.adapter = adapter
            recyclerClientSearch.layoutManager = LinearLayoutManager(context)

            adapter.setOnClientChosenListener { id, name ,totalDebt ->
                listener?.invoke(id, name,totalDebt)
                close()
            }

            cancelClientSearch.setOnClickListener {
                close()
            }

            val handler = Handler()
            searchClientForSaleView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    handler.removeCallbacksAndMessages(null)
                    if (query != null) {
                        querySt = query.trim()
                        adapter.submitList(clients.filter {
                            it.client.name.contains(
                                querySt,
                                true
                            )
                        })
                        adapter.query = querySt
                        recyclerClientSearch.adapter = adapter
                        recyclerClientSearch.layoutManager = LinearLayoutManager(context)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        if (newText != null) {
                            querySt = newText.trim()
                            adapter.submitList(clients.filter {
                                it.client.name.contains(
                                    querySt,
                                    true
                                )
                            })
                            adapter.query = querySt
                            recyclerClientSearch.adapter = adapter
                            recyclerClientSearch.layoutManager = LinearLayoutManager(context)
                        }
                    }, 500)
                    return true
                }
            })

            val closeButton = searchClientForSaleView.findViewById(R.id.search_close_btn) as ImageView
            closeButton.setOnClickListener {
                searchClientForSaleView.setQuery(null, false)
                searchClientForSaleView.clearFocus()
              //requiredActivity.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }
    }

    fun setOnClientChosen(f: ((Int, String,Int) -> Unit)?) {
        listener = f
    }
}

