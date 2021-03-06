package com.example.liderstoreagent.ui.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.data.models.getproduct.Discount
import com.example.liderstoreagent.data.models.getproduct.productFullData
import com.example.liderstoreagent.data.models.sellmodel.SellProductData
import com.example.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.example.liderstoreagent.ui.dialogs.ClientChooseDialog
import com.example.liderstoreagent.ui.dialogs.DiscountsDialog
import com.example.liderstoreagent.ui.viewmodels.ClientPageViewModel
import com.example.liderstoreagent.ui.viewmodels.SellProductViewModel
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.product_sell_fragment.*

class MarketPage : Fragment(R.layout.product_sell_fragment) {

    var productName = ""
    var productId = -1
    var clientId = -1
    var discountValue = 0
    var discounts: List<Discount> = ArrayList()
    private val viewModel: ClientPageViewModel by viewModels()
    var clientsData: List<ClientsData> = ArrayList()

    private val sellViewModel: SellProductViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (productId <= 0) cardView.visibility = View.GONE

        clientsSetUp()
        getProductSetUp()
        sellProductSetUp()
        TextChanged(inputQuantity)
        TextChanged(inputPrice)


        chooseClient.setOnClickListener {
            if (clientsData.isNotEmpty()) {
                clientChosenChooseDialog(clientsData)
            } else requireActivity().showToast("Xaridorlar listi bo'sh!")
        }

        sellProduct.setOnClickListener {
            when {
                inputQuantity.text.toString().isEmpty() -> {
                    inputQuantity.error = "Miqdorini kiriting!"
                }
                inputPrice.text.toString().isEmpty() -> {
                    inputPrice.error = "Narxini kiriting!"
                }
                clientName.text.isEmpty() -> requireActivity().showToast("Xaridorni tanlang!")
                else -> {
                    val quantity = inputQuantity.text.toString().toDouble()
                    val price = inputPrice.text.toString().toDouble()
                    productName
                    clientId
                    if (quantity > 0 && price > 0) {
                        val sellData = SellProductData(
                            "%.2f".format(price - price * discountValue / 100),
                            "%.2f".format(quantity),
                            "",
                            "",
                            clientId,
                            productId
                        )
                        sellViewModel.sellProduct(sellData)
                    } else requireActivity().showToast("Yaroqli miqdor yoki narxni kiriting!")
                }
            }
        }


        discountButton.setOnClickListener {
            val dialog = DiscountsDialog(requireContext(), discounts)
            dialog.clickListener { it ->
                discountValue = it
                discountText.text = it.toString() + " %"
                setTotalPrice()
            }
            dialog.show()
        }

    }

    private val progressProductObserver = Observer<Boolean> {
        if (it) {
            sellProgressBar.visibility = View.VISIBLE
        } else {
            sellProgressBar.visibility = View.GONE
        }
    }

    private val errorProductObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        sellProgressBar.visibility = View.GONE
    }


    private val connectionErrorProductObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }


    @SuppressLint("SetTextI18n")
    private val successProductObserver = Observer<productFullData> { response ->
        if (response.discount.isNotEmpty()) {
            discountLayout.visibility = View.VISIBLE
            discountText.text = "0 %"
            discountValue = 0
        } else discountLayout.visibility = View.GONE
        cardView.visibility = View.VISIBLE
        sellTitle.visibility = View.VISIBLE
        sellProductName.text = response.product.name
        unitText.text = response.product.unit
        productName = response.product.name
        totalAmount.text = response.product.quantity.toString() + " ${response.product.unit}"
        productId = response.product.id
        discounts = response.discount
    }

    private val progressSellObserver = Observer<Boolean> {
        if (it) {
            sellProgressBar.visibility = View.VISIBLE
        } else {
            sellProgressBar.visibility = View.GONE
        }
    }


    private val errorSellObserver = Observer<String> {
        requireActivity().showToast("Ulanishda xatolik!")
        sellProgressBar.visibility = View.GONE
    }
    private val connectionErrorSellObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }
    private val successSellObserver = Observer<SellProductResponse> { response ->
        if (response.client == clientId && response.product == productId) {
            initDialog()
        }

    }

    private fun initDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("So'rovingiz muvaffqiyatli amalga oshdi. Tasdiqlash uchun omborchiga yuborildi!")
            .setPositiveButton("Ok") { dialog, _ ->
                productName = ""
                productId = -1
                clientId = -1
                discountValue = 0
                discountText.text = "0 %"
                inputQuantity.setText("")
                inputPrice.setText("")
                cardView.visibility = View.GONE
                sellTitle.visibility = View.GONE
                dialog.cancel()
            }.show()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun sellProductSetUp() {
        sellViewModel.progressSellLiveData.observe(this, progressSellObserver)
        sellViewModel.errorNotResponseLiveData.observe(this, errorSellObserver)
        sellViewModel.connectionErrorLiveData.observe(
            this,
            connectionErrorSellObserver
        )
        sellViewModel.errorResponseLiveData.observe(this, errorResponseObserver)
        sellViewModel.successLiveData.observe(this, successSellObserver)
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun getProductSetUp() {
        sellViewModel.progressProductLiveData.observe(this, progressProductObserver)
        sellViewModel.connectionErrorProductLiveData.observe(
            this,
            connectionErrorProductObserver
        )
        sellViewModel.successProductLiveData.observe(this, successProductObserver)
        sellViewModel.errorProductLiveData.observe(this, errorProductObserver)
    }

    private val errorResponseObserver = Observer<String> {
        inputQuantity.setText("")
        inputPrice.setText("")
        requireContext().showToast(it)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            sellProgressBar.visibility = View.VISIBLE
        } else {
            sellProgressBar.visibility = View.GONE
        }
    }
    private val errorClientsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
        sellProgressBar.visibility = View.GONE
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }
    private val successClientsObserver = Observer<List<ClientsData>> { list ->
        clientsData = list
    }

    private fun clientChosenChooseDialog(data: List<ClientsData>) {
        val dialog = ClientChooseDialog(requireContext(), data)
        dialog.show()
        dialog.setOnClientChosen { id, name, totalDebt ->
            clientName.text = name
            if (totalDebt > 0) {
                //dateLinear.visibility = View.VISIBLE
            } else dateLinear.visibility = View.GONE
            clientId = id
        }
    }

    private fun clientsSetUp() {
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorCategoriesLiveData.observe(viewLifecycleOwner, errorClientsObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successClientsObserver)
    }

    fun handlerEvent(id: Int) {
        sellViewModel.getProduct(id.toString())
    }

    fun TextChanged(editText: EditText) {

        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setTotalPrice()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalPrice() {
        if (inputQuantity.text.toString().isNotEmpty() && inputPrice.text.toString().isNotEmpty()) {
            val quantity = inputQuantity.text.toString().toDouble()
            val price = inputPrice.text.toString().toDouble()
            if (quantity > 0.0 && price > 0.0)
                totalPrice.text =
                    "%.1f".format(quantity * (price - price * discountValue / 100)) + " so'm"
        } else totalPrice.text = ""
    }
}