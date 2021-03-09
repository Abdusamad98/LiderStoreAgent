package com.example.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.salarymodel.SalaryData
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.viewmodels.SalaryViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val viewModel: SalaryViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agent_firstname.text = TokenSaver.getFirstName()
        agent_lastname.text = TokenSaver.getLastName()
        agent_phone.text = TokenSaver.getLogin()

        soldHistorySetUp()

        backToHomeProfile.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            salaryProgressBar.visibility = View.VISIBLE
        } else {
            salaryProgressBar.visibility = View.GONE
        }
    }

    private val errorSalaryObserver = Observer<String> {
        requireActivity().showToast(it)
        salaryProgressBar.visibility = View.GONE
    }

    private val errorSalObserver = Observer<String> {
        requireActivity().showToast(it)
        salaryProgressBar.visibility = View.GONE
    }

    private val successHistoryObserver = Observer<SalaryData> { salaryData ->
        fixedSalary.text = salaryData.fixed_salary + " so'm"
        flexibleSalary.text = salaryData.flexible_salary + " so'm"
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun soldHistorySetUp() {
        viewModel.progressSellLiveData.observe(this, progressObserver)
        viewModel.errorNotResponseLiveData.observe(this, errorSalObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successHistoryObserver)
        viewModel.errorResponseLiveData.observe(this, errorSalaryObserver)
    }




}