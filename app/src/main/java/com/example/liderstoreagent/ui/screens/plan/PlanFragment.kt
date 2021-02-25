package com.example.liderstoreagent.ui.screens.plan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.planmodel.PlanData
import com.example.liderstoreagent.ui.adapters.plan.PlansAdapter
import com.example.liderstoreagent.ui.viewmodels.plan.PlanViewModel
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.plan_fragment.*


class PlanFragment : Fragment(R.layout.plan_fragment) {
    private val viewModel: PlanViewModel by viewModels()
    lateinit var recycler: RecyclerView
    private var planData: List<PlanData> = ArrayList()
    private val planAdapter by lazy { PlansAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plansSetUp()
        recycler = view.findViewById(R.id.recyclerPlans)
        viewModel.getPlans()
        backToHomePlan.setOnClickListener {
            findNavController().navigateUp()
        }

        planAdapter.setOnPlanClicked {
            requireContext().showToast("$it")
            findNavController().navigate(
                PlanFragmentDirections.actionPlanFragmentToPlanDetailsFragment(
                    it
                )
            )
        }
    }

    private fun initRecycler(plans: List<PlanData>) {
        planAdapter.submitList(plans)
        recyclerPlans.layoutManager = LinearLayoutManager(requireContext())
        recyclerPlans.adapter = planAdapter
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            plansProgressBar.visibility = View.VISIBLE
        } else {
            plansProgressBar.visibility = View.GONE
        }
    }

    private val errorHistoryObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        plansProgressBar.visibility = View.GONE
    }

    private val successHistoryObserver = Observer<List<PlanData>> { plans ->
        planData = plans
        initRecycler(planData)
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun plansSetUp() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorPlanLiveData.observe(this, errorHistoryObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successHistoryObserver)
    }


}