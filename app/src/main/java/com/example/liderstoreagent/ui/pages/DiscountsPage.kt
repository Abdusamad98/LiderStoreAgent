package com.example.liderstoreagent.ui.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.example.liderstoreagent.data.models.discountsmodel.Discounts
import com.example.liderstoreagent.ui.adapters.DiscountedProductListAdapter
import com.example.liderstoreagent.ui.dialogs.DiscountChooseDialog
import com.example.liderstoreagent.ui.viewmodels.DiscountsPageViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.discounts_fragment.*

@Suppress("DEPRECATION")
class DiscountsPage : Fragment(R.layout.discounts_fragment) {


    private val discountViewModel: DiscountsPageViewModel by viewModels()
    lateinit var discountedProducts: List<DiscountedProduct>
    lateinit var discountsAdapter: DiscountedProductListAdapter
    var chosenDiscount = 0
    var discounts: List<Discounts> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discountedProducts = ArrayList<DiscountedProduct>()
        discountsSetUp()
        discountedProductsSetUp()



        searchByDiscount.setOnClickListener {
            if (discounts.isEmpty()) {
                requireActivity().showToast("Hali data kelmadi!")
            } else {
                initDiscountsChooseDialog()
            }
        }


        refreshDiscounts.setOnRefreshListener {
           if(chosenDiscount!=0) discountViewModel.getDiscountedProducts(chosenDiscount)
            Handler().postDelayed(Runnable {
                refreshDiscounts.isRefreshing = false
            }, 2000)
        }

    }


    private fun discountsSetUp() {
        discountViewModel.progressDiscountsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountedProductsObserver
        )
        discountViewModel.errorDiscountsLiveData.observe(viewLifecycleOwner, errorDiscountsObserver)
        discountViewModel.connectionErrorDiscountsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorDiscountsObserver
        )
        discountViewModel.successDiscountsLiveData.observe(
            viewLifecycleOwner,
            successDiscountsObserver
        )
    }


    private val errorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        discountsProgressBar.visibility = View.GONE
    }

    private val connectionErrorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    @SuppressLint("SetTextI18n")
    private val successDiscountsObserver = Observer<List<Discounts>> { discountsList ->
        discounts = discountsList

        if (!discounts.isEmpty()) {
            chosenDiscount = discounts[0].id
            discountViewModel.getDiscountedProducts(discounts[0].id)
            log(discounts[0].toString(), "DIS")
            discountName.text = discounts[0].name
            discount.text = discounts[0].discount.toString() + " %"
            if (discounts[0].deadline.equals(null)) deadline.text = "Noma'lum" else deadline.text =
                discounts[0].deadline.toString()//.substring(0,10)
        }
    }


    private fun initDiscountsChooseDialog() {
        val dialog = DiscountChooseDialog(requireContext(), discounts)
        dialog.show()
        dialog.setDiscountChosen { id1, name1, discount1, deadline1 ->
            discountName.text = name1
            deadline.text = deadline1
            discount.text = discount1
            discountViewModel.getDiscountedProducts(id1)
            chosenDiscount = id1
            requireActivity().showToast("id = $id1")
        }
    }

    private val progressDiscountedProductsObserver = Observer<Boolean> {
        if (it) {
            discountsProgressBar.visibility = View.VISIBLE
        } else {
            discountsProgressBar.visibility = View.GONE
        }
    }
    private val errorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
    }
    private val connectionErrorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }
    private val successDiscountedProductsObserver =
        Observer<List<DiscountedProduct>> { discountedProductList ->
            discountedProducts = discountedProductList
            if (discountedProducts.isNotEmpty()) {
                initProductsList(discountedProducts)
            }
        }

    private fun discountedProductsSetUp() {
        discountViewModel.progressDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountedProductsObserver
        )
        discountViewModel.errorDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            errorDiscountedProductsObserver
        )
        discountViewModel.connectionErrorDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorDiscountedProductsObserver
        )
        discountViewModel.successDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            successDiscountedProductsObserver
        )
    }


    private fun initProductsList(data: List<DiscountedProduct>) {
        discountsAdapter = DiscountedProductListAdapter()
        discountsAdapter.submitList(data)
        recyclerDiscountedProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerDiscountedProducts.adapter = discountsAdapter
    }
}