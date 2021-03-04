package com.example.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.viewmodels.AddClientViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.add_client_page.*
import java.io.File


class AddClientsPage : Fragment(R.layout.add_client_page) {

    var inputMarketName = ""
    var address = ""
    var repsonsiblePerson = ""
    var phone1 = ""
    var phone2 = ""
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    var supposedAmount = 0
    var imageAddress: File? = null


    private val viewModel: AddClientViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpReport()

        backToHomeAddClient.setOnClickListener {
            findNavController().navigateUp()
        }


        pickClientImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .saveDir(
                    File(
                        requireContext()
                            .getExternalFilesDir(null)?.let { it.absolutePath }, "DotanationBox"
                    )
                )
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            imageAddress = ImagePicker.getFile(data)!!
                            requireContext().showToast("Rasm tanlandi!")
                            log("$imageAddress", "RASM")
                            selectedImageClient.setImageURI(Uri.fromFile(imageAddress))
                            selectedImageClient.visibility = View.VISIBLE
                        }
                        else -> {

                        }
                    }
                }
        }


        pickClientLocation.setOnClickListener {
            findNavController().navigate(R.id.action_addClientsPage_to_mapsFragment)
        }

        sendClientData.setOnClickListener {
            inputMarketName = marketName.text.toString()
            address = clientAddress.text.toString()
            repsonsiblePerson = responsiblePerson.text.toString()
            phone1 = phoneNumber1.text.toString()
            phone2 = phoneNumber2.text.toString()
            if (assumptionValue.text.toString().isNotEmpty()) supposedAmount =
                assumptionValue.text.toString().toInt()

            if (inputMarketName.isEmpty()) {
                marketName.error = "Xaridor tomon nomi"
            } else if (address.isEmpty()) {
                clientAddress.error = "Manzili"
            } else if (repsonsiblePerson.isEmpty()) {
                responsiblePerson.error = "Ma'sul agent"
            } else if (phone1.isEmpty()) {
                phoneNumber1.error = "Telefoni raqami 1"
            } else if (assumptionValue.text.toString().isEmpty()) {
                assumptionValue.error = "Taxminiy aylanma summasi"
            } else if (latitude == 0.0 || longitude == 0.0) {
                requireContext().showToast("Lokatsiya olinmagan!")
            } else if (imageAddress == null) {
                requireContext().showToast("Rasmni tanlang!")
            } else {
                val d = AddClientData(
                    inputMarketName,
                    address,
                    repsonsiblePerson,
                    phone1,
                    phone2,
                    longitude,
                    latitude,
                    imageAddress!!,
                    supposedAmount,
                    TokenSaver.getAgentId()
                )
                viewModel.addClient(d)
                requireContext().showToast("Kuting!")
            }
        }
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            clientAddProgressBar.visibility = View.VISIBLE
        } else {
            clientAddProgressBar.visibility = View.GONE
        }
    }

    private val errorAddClientObserver = Observer<String> {
        requireActivity().showToast("Xatolik!")
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    private val successObserver = Observer<Any> {
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("Diqqat xaridor ma'lumotlari yuborildi!")
            .setPositiveButton("Ok") { dialog, _ ->
                findNavController().navigateUp()
                dialog.cancel()
            }.show()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpReport() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorAddClientLiveData.observe(this, errorAddClientObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.locationLiveData.observe(this, locationObserver)
    }

    private val locationObserver = Observer<String> {
        requireContext().showToast(it)

    }

}