package com.example.liderstoreagent.ui.screens.report

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
import com.example.liderstoreagent.data.models.reportmodel.ReportData
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.viewmodels.report.ReportViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.report_fragment.*
import java.io.File

class ReportFragment : Fragment(R.layout.report_fragment) {
    private val viewModel: ReportViewModel by viewModels()
    var inputComm = ""
    var imageAddress: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHomeReport.setOnClickListener {
            findNavController().navigateUp()
        }
        setUpReport()

        pickImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .saveDir(
                    File(
                        requireContext()
                            .getExternalFilesDir(null)?.let { it.absolutePath }, "DotanationBox"
                    )
                )
//            .maxResultSize(1080, 1920)
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            imageAddress = ImagePicker.getFile(data)!!
                            requireContext().showToast("Rasm tanlandi!")
                            log("$imageAddress", "RASM")
                            selectedImage.setImageURI(Uri.fromFile(imageAddress))
                            selectedImage.visibility = View.VISIBLE
                        }

                        else -> {

                        }
                    }
                }


        }

        sendReport.setOnClickListener {
            inputComm = inputComment.text.toString()
            if (inputComm.isNotEmpty()) {
                sendPackage()
            } else requireContext().showToast("Hisobot textini kiriting!")
        }
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            reportProgressBar.visibility = View.VISIBLE
        } else {
            reportProgressBar.visibility = View.GONE
        }
    }

    private val errorLoginObserver = Observer<String> {
        requireActivity().showToast("Xatolik!")
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
    }

    private val successObserver = Observer<Any> {
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("Diqqat hisobot yuborildi !")
            .setPositiveButton("Ok") { dialog, _ ->
                findNavController().navigateUp()
                dialog.cancel()
            }.show()
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpReport() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorReportLiveData.observe(this, errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successObserver)
    }


    private fun sendPackage() {
        viewModel.sendPackage(ReportData(inputComm, imageAddress, TokenSaver.getAgentId()))
    }
}