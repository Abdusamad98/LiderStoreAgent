package com.example.liderstoreagent.ui.screens.report

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import com.example.liderstoreagent.ui.adapters.reporthistory.ReportHistoryAdapter
import com.example.liderstoreagent.ui.viewmodels.reporthistory.ReportHistoryViewModel
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.history_reports_fragment.*

class ReportsHistoryPage : Fragment(R.layout.history_reports_fragment) {
    private val viewModel: ReportHistoryViewModel by viewModels()
    private val historyReportAdapter by lazy { ReportHistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHomeReportsHistory.setOnClickListener {
            findNavController().navigateUp()
        }
        reportHistorySetUp()
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            reportHistoryProgressBar.visibility = View.VISIBLE
        } else {
            reportHistoryProgressBar.visibility = View.GONE
        }
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    private val errorHistoryObserver = Observer<String> {
        requireActivity().showToast(it)
        reportHistoryProgressBar.visibility = View.GONE
    }

    private val successHistoryGet = Observer<List<ReportHistory>> { historyList ->
        initRecycler(historyList)
    }

    @SuppressLint("FragmentLiveDataObserve")

    fun reportHistorySetUp() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorHistoryObserver)
        viewModel.successLiveDataGet.observe(this, successHistoryGet)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
    }


    private fun initRecycler(history: List<ReportHistory>) {
        historyReportAdapter.submitList(history)
        recyclerReportHistory.layoutManager = LinearLayoutManager(requireContext())
        recyclerReportHistory.adapter = historyReportAdapter
    }


}