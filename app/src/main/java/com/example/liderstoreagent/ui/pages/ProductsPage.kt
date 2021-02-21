package com.example.liderstoreagent.ui.pages

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.categorymodel.CategoryData
import com.example.liderstoreagent.data.models.productsmodel.ProductData
import com.example.liderstoreagent.ui.adapters.ProductListAdapter
import com.example.liderstoreagent.ui.dialogs.CategoryChooseDialog
import com.example.liderstoreagent.ui.viewmodels.ProductsPageViewModel
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.products_fragment.*

@Suppress("DEPRECATION")
class ProductsPage : Fragment(R.layout.products_fragment) {
    private val pageViewModel: ProductsPageViewModel by viewModels()
    lateinit var recycler: RecyclerView
    lateinit var categories: ArrayList<CategoryData>
    private val productsAdapter by lazy { ProductListAdapter() }
    var productData: List<ProductData> = ArrayList()

    private var querySt = ""
    private var eventListener: ((Int, String, String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recyclerProducts)

        categories = ArrayList<CategoryData>()

        categorySetUp()
        productsSetUp()


        refreshProducts.setOnRefreshListener {
            pageViewModel.getCategories()
            pageViewModel.getProducts(1)
            Handler().postDelayed(Runnable {
                refreshProducts.isRefreshing = false
            }, 3000)
        }


        searchByCategory.setOnClickListener {
            if (categories.isEmpty()) {
                requireActivity().showToast("Internetni yoqing va refresh qiling!")
            } else initCategoryChooseDialog(categories)
        }
        productsAdapter.clickedProduct { id, name, unit ->
            eventListener?.invoke(id, name,unit)
        }
        val handler = Handler()
        searchProductView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query != null) {
                    querySt = query.trim()
                    initProductsList(productData.filter { it.name.contains(querySt, true) })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if (newText != null) {
                        querySt = newText.trim()
                        initProductsList(productData.filter { it.name.contains(querySt, true) })
                    }
                }, 500)
                return true
            }
        })
        val closeButton = searchProductView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            pageViewModel.closeSearch()
        }

    }

    private val closeSearchObserver = Observer<Unit> {
        searchProductView.setQuery(null, false)
        searchProductView.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            productsProgressBar.visibility = View.VISIBLE
        } else {
            productsProgressBar.visibility = View.GONE
        }
    }

    private val errorCategoriesObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        productsProgressBar.visibility = View.GONE
    }

    private val successCategoriesObserver = Observer<List<CategoryData>> { category ->
        categories = category as ArrayList<CategoryData>
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    fun categorySetUp() {
        //  pageViewModel.progressCategoriesLiveData.observe(viewLifecycleOwner, progressObserver)
        pageViewModel.errorCategoriesLiveData.observe(viewLifecycleOwner, errorCategoriesObserver)
        pageViewModel.connectionErrorCategoriesLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )
        pageViewModel.successCategoriesLiveData.observe(
            viewLifecycleOwner,
            successCategoriesObserver
        )
    }


    private val errorProductsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
    }

    private val successProductsObserver = Observer<List<ProductData>> { products ->
        productData = products
        initProductsList(products)
    }


    fun productsSetUp() {
        pageViewModel.progressProductsLiveData.observe(viewLifecycleOwner, progressObserver)
        pageViewModel.closeLiveData.observe(viewLifecycleOwner, closeSearchObserver)
        pageViewModel.errorProductsLiveData.observe(viewLifecycleOwner, errorProductsObserver)
        pageViewModel.successProductsLiveData.observe(viewLifecycleOwner, successProductsObserver)
    }

    fun initProductsList(data: List<ProductData>) {
        productsAdapter.submitList(data)
        productsAdapter.query = querySt
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = productsAdapter
    }

    private fun initCategoryChooseDialog(data: List<CategoryData>) {
        val dialog = CategoryChooseDialog(requireContext(), data)
        dialog.show()
        dialog.setOnCategoryChosen { id ->
            pageViewModel.getProducts(id)
            requireActivity().showToast("id = $id")
        }
    }

    fun eventListener(f: (Int, String,String) -> Unit) {
        eventListener = f
    }


}