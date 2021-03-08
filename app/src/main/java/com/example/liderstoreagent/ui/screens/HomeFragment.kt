package com.example.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.adapters.MainPageAdapter
import com.example.liderstoreagent.ui.viewmodels.HomeFragmentViewModel
import com.example.liderstoreagent.utils.pageChangeListener
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_nav.*

class HomeFragment : Fragment(R.layout.main_nav) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val adapter by lazy { MainPageAdapter(childFragmentManager) }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agentName.setText("Agent: " + TokenSaver.getFirstName())

        addClient.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addClientsPage)
        }

        pager.adapter = adapter
        pager.pageChangeListener {
            viewModel.selectPagePosition(it)
        }

        val selectPageObserver = Observer<Int> {
            pager.currentItem = it
            when (it) {
                0 -> bottomNavigationView.selectedItemId = R.id.clients
                1 -> bottomNavigationView.selectedItemId = R.id.products
                2 -> bottomNavigationView.selectedItemId = R.id.Discounts
                3 -> bottomNavigationView.selectedItemId = R.id.market
                else -> bottomNavigationView.selectedItemId = R.id.clients
            }
        }

        adapter.eventListener { id  ->
            pager.currentItem = 3
            adapter.marketPage.handlerEvent(id)
        }

        viewModel.selectPageLiveData.observe(viewLifecycleOwner, selectPageObserver)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.clients -> viewModel.selectPagePosition(0)
                R.id.products -> viewModel.selectPagePosition(1)
                R.id.Discounts -> viewModel.selectPagePosition(2)
                R.id.market -> viewModel.selectPagePosition(3)
                else -> viewModel.selectPagePosition(0)
            }
            return@setOnNavigationItemSelectedListener true
        }

        loadView()
    }


    fun loadView() {
        menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        image.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }

        profile.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        }

        exit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Diqqat!")
                .setMessage("Tizimdan chiqishni hohlaysizmi?")
                .setNegativeButton("Yo'q") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("Ha") { dialog, _ ->
                    TokenSaver.token = ""
                    TokenSaver.setAgentId(0)
                    TokenSaver.setLogin("")
                    TokenSaver.setPassword("")
                    requireActivity().finish()
                    dialog.cancel()
                }.show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        soldProductsByClients.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_clientProductsFragment)
        }

        soldProducts.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_soldProductsFragment)
        }

        plan.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_planFragment)
        }

        report.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_reportFragment)
        }

        historyReports.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_reportsHistoryPage)
        }

    }



}