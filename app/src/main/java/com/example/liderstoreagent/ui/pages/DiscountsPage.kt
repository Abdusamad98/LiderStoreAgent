package com.example.liderstoreagent.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.example.liderstoreagent.data.models.discountsmodel.Discounts
import com.example.liderstoreagent.data.models.productsmodel.ProductData
import com.example.liderstoreagent.ui.adapters.DiscountedProductListAdapter
import com.example.liderstoreagent.ui.adapters.ProductListAdapter
import com.example.liderstoreagent.ui.dialogs.DiscountChooseDialog
import com.example.liderstoreagent.ui.viewmodels.DiscountsPageViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.discounts_fragment.*
import kotlinx.android.synthetic.main.products_fragment.*

class DiscountsPage : Fragment(R.layout.discounts_fragment) {


    private val discountViewModel: DiscountsPageViewModel by viewModels()
    lateinit var discountedProducts: List<DiscountedProduct>
    lateinit var discountsAdapter: DiscountedProductListAdapter
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

    }


    private fun discountsSetUp() {
        discountViewModel.progressDiscountsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountObserver
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


    private val progressDiscountObserver = Observer<Boolean> {

    }

    private val errorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
    }

    private val connectionErrorDiscountsObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }
    private val successDiscountsObserver = Observer<List<Discounts>> { discountsList ->
        discounts = discountsList
        if (!discounts.isEmpty()) {
            log(discounts[0].toString(), "DIS")
            discountName.text = discounts[0].name
            discount.text = discounts[0].discount.toString() + " %"
          if(discounts[0].deadline.equals(null)) deadline.text ="Noma'lum"  else deadline.text = discounts[0].deadline.toString()//.substring(0,10)
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
            requireActivity().showToast("id = $id1")
        }
    }

    private val progressDiscountedProductsObserver = Observer<Boolean> {
        if (it) {
            discountsProgressBar.visibility =View.VISIBLE
        } else {
            discountsProgressBar.visibility =View.GONE
        }
    }
    private val errorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
    }
    private val connectionErrorDiscountedProductsObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }
    private val successDiscountedProductsObserver = Observer<List<DiscountedProduct>> { discountedProductList ->
        discountedProducts = discountedProductList
        if (!discountedProducts.isEmpty()) {
            initProductsList(discountedProducts)
        }
    }

    private fun discountedProductsSetUp() {
        discountViewModel.progressDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            progressDiscountedProductsObserver
        )
        discountViewModel.errorDiscountedProductsLiveData.observe(viewLifecycleOwner, errorDiscountedProductsObserver)
        discountViewModel.connectionErrorDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            connectionErrorDiscountedProductsObserver
        )
        discountViewModel.successDiscountedProductsLiveData.observe(
            viewLifecycleOwner,
            successDiscountedProductsObserver
        )
    }


    fun initProductsList(data: List<DiscountedProduct>) {
        discountsAdapter = DiscountedProductListAdapter()
        discountsAdapter.submitList(data)
        recyclerDiscountedProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerDiscountedProducts.adapter = discountsAdapter
    }
}