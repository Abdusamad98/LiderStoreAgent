package com.example.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.liderstoreagent.R
import com.example.liderstoreagent.ui.adapters.MainPageAdapter
import com.example.liderstoreagent.utils.pageChangeListener
import com.example.liderstoreagent.viewmodels.HomeFragmentViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val adapter by lazy { MainPageAdapter(childFragmentManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        pager.adapter = adapter
        pager.pageChangeListener {
            viewModel.selectPagePosition(it)
        }

        val selectPageObserver = Observer<Int> {
            pager.currentItem = it
            when (it) {
                0 -> bottomNavigationView.selectedItemId = R.id.clients
                1 -> bottomNavigationView.selectedItemId = R.id.chicken
                2 -> bottomNavigationView.selectedItemId = R.id.sausage
                else -> bottomNavigationView.selectedItemId = R.id.milk
            }
        }

        viewModel.selectPageLiveData.observe(viewLifecycleOwner, selectPageObserver)

        bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.clients -> viewModel.selectPagePosition(0)
                    R.id.chicken -> viewModel.selectPagePosition(1)
                    R.id.sausage -> viewModel.selectPagePosition(2)
                    R.id.milk -> viewModel.selectPagePosition(3)
                    else -> viewModel.selectPagePosition(4)
                }
                    return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}