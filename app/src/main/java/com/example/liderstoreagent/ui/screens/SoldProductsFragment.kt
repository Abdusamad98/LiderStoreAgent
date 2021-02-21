package com.example.liderstoreagent.ui.screens
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.example.liderstoreagent.ui.adapters.SoldHistoryListAdapter
import com.example.liderstoreagent.ui.viewmodels.SoldHistoryViewModel
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.product_sold_fragment.*

@Suppress("DEPRECATION")
class SoldProductsFragment : Fragment(R.layout.product_sold_fragment) {
    private var querySt = ""
    private val viewModel:SoldHistoryViewModel by viewModels()
    lateinit var recycler:RecyclerView
    var soldProducts:List<SoldProductHistory> =ArrayList()
    private val soldProductsAdapter by lazy{SoldHistoryListAdapter()}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       soldHistorySetUp()
        recycler = view.findViewById(R.id.recyclerHistory)



        swipeHistory.setOnRefreshListener {
              viewModel.getHistory()
            Handler().postDelayed(Runnable {
                swipeHistory.isRefreshing = false
            }, 3000)
        }


        val handler = Handler()
        searchHistoryView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initRecycler(soldProducts.filter {
                        it.product_name.contains(
                            querySt,
                            true
                        )
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if (newText != null) {
                        querySt = newText.trim()
                        initRecycler(soldProducts.filter {
                            it.product_name.contains(
                                querySt,
                                true
                            )
                        })
                    }
                }, 500)
                return true
            }
        })

    }

    private fun initRecycler(soldProducts: List<SoldProductHistory>) {
        soldProductsAdapter.submitList(soldProducts)
        soldProductsAdapter.query = querySt
        recyclerHistory.layoutManager = LinearLayoutManager(requireContext())
        recyclerHistory.adapter = soldProductsAdapter
    }


    private val closeSearchObserver = Observer<Unit> {
        searchHistoryView.setQuery(null, false)
        searchHistoryView.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            historyProgressBar.visibility = View.VISIBLE
        } else {
            historyProgressBar.visibility = View.GONE
        }
    }

    private val errorHistoryObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        historyProgressBar.visibility = View.GONE
    }

    private val successHistoryObserver = Observer<List<SoldProductHistory>> { historyList ->
        soldProducts = historyList
        initRecycler(soldProducts)
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun soldHistorySetUp(){
        viewModel.progressLiveData.observe(this,progressObserver)
        viewModel.errorHistoryLiveData.observe(this,errorHistoryObserver)
        viewModel.connectionErrorLiveData.observe(this,connectionErrorObserver)
        viewModel.successLiveData.observe(this,successHistoryObserver)
        viewModel.closeLiveData.observe(this,closeSearchObserver)
    }


}