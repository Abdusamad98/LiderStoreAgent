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
import com.example.liderstoreagent.data.models.sellmodel.SellProductData
import com.example.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.example.liderstoreagent.ui.dialogs.ClientChooseDialog
import com.example.liderstoreagent.ui.viewmodels.ClientPageViewModel
import com.example.liderstoreagent.ui.viewmodels.SellProductViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.product_sell_fragment.*
import kotlinx.android.synthetic.main.products_fragment.*

class MarketPage : Fragment(R.layout.product_sell_fragment) {

    var productName = ""
    var productId = -1
    var clientId = -1
    private val viewModel: ClientPageViewModel by viewModels()
    var clientsData: List<ClientsData> = ArrayList()

    private val sellViewModel: SellProductViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showToast("Assalomu Aleykum!")
        clientsSetUp()
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
                    if (quantity > 0 && 0 < price) {
                        val sellData = SellProductData("%.2f".format(price ),"%.2f".format(quantity ), "", "", clientId, productId)
                        sellViewModel.sellProduct(sellData)
                    } else requireActivity().showToast("Yaroqli miqdor yoki narxni kiriting!")

                }
            }
        }


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
        if(response.client == clientId && response.product == productId){
            initDialog()
        }

    }
   private fun initDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("So'rovingiz muvaffqiyatli amalga oshdi. Tasdiqlash uchun omborchiga yuborildi!")
            .setPositiveButton("Ok") { dialog, _ ->
                productName = ""
                productId = -1
                clientId = -1
                inputQuantity.setText("")
                inputPrice.setText("")
                cardView.visibility = View.GONE
                dialog.cancel()
            }.show()
    }

   @SuppressLint("FragmentLiveDataObserve")
   private fun sellProductSetUp() {
        sellViewModel.progressSellLiveData.observe(viewLifecycleOwner, progressSellObserver)
        sellViewModel.errorNotResponseLiveData.observe(viewLifecycleOwner, errorSellObserver)
        sellViewModel.connectionErrorLiveData.observe(
            viewLifecycleOwner,
            connectionErrorSellObserver
        )
       sellViewModel.errorResponseLiveData.observe(this,errorResponseObserver)
        sellViewModel.successLiveData.observe(viewLifecycleOwner, successSellObserver)
    }

    private val errorResponseObserver = Observer<String> {
        inputQuantity.setText("")
        inputPrice.setText("")
        requireContext().showToast(it)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) requireActivity().showToast("Kilentlar Olindi")
    }
    private val errorClientsObserver = Observer<Unit> {
        requireActivity().showToast("Ulanishda xatolik!")
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
                dateLinear.visibility = View.VISIBLE
            } else dateLinear.visibility = View.GONE
            clientId = id
            requireActivity().showToast("id = $id + $name + $totalDebt")
        }
    }

   private fun clientsSetUp() {
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorCategoriesLiveData.observe(viewLifecycleOwner, errorClientsObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successClientsObserver)
    }

    fun handlerEvent(id: Int, name: String, unit: String) {
        cardView.visibility = View.VISIBLE
        requireContext().showToast("marker screen id = $id + $name + $unit")
        sellProductName.text = name
        unitText.text = unit
        productName = name
        productId = id
    }

    fun TextChanged(editText: EditText) {

        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setTotalPrice()
            }
        })
    }

   private fun setTotalPrice() {
        if (inputQuantity.text.toString().isNotEmpty() && inputPrice.text.toString().isNotEmpty()) {
            val quantity = inputQuantity.text.toString().toDouble()
            val price = inputPrice.text.toString().toDouble()
            if (quantity > 0.0 && price > 0.0) totalPrice.text =
                "%.1f".format(quantity * price) + " so'm"
        } else totalPrice.text = ""
    }


}