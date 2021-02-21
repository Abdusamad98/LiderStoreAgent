package com.example.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R
import com.example.liderstoreagent.ui.adapters.MainPageAdapter
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.utils.pageChangeListener
import com.example.liderstoreagent.utils.showToast
import com.example.liderstoreagent.ui.viewmodels.HomeFragmentViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_nav.*


class HomeFragment : Fragment(R.layout.main_nav) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val adapter by lazy { MainPageAdapter(childFragmentManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agentName.setText("Agent: " + TokenSaver.getFirstName())


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

        adapter.eventListener { id, name, unit ->
            pager.currentItem = 3
            adapter.marketPage.handlerEvent(id, name, unit)
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
            drawerLayout.closeDrawer(GravityCompat.START)
            TokenSaver.token = ""
            TokenSaver.setAgentId(0)
            TokenSaver.setLogin("")
            TokenSaver.setPassword("")
            requireActivity().finish()
        }
        soldProducts.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_mainFragment_to_soldProductsFragment)
        }

    }

}