package com.example.liderstoreagent.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.add_client_page.*
import java.io.File

//(dokon nomi,
// mas'ul shaxs,
//tel raqami (ikkita nomer olishi) faqat bittasi requared*,
// geo location,
// dokon rasmi,
// taxminiy qancha oborot qilishi)


class AddClientsPage : Fragment(R.layout.add_client_page) {

    var inputMarketName = ""
    var repsonsiblePerson = ""
    var phone1 = ""
    var phone2 = ""
    var supposedAmount: Double = 0.0
    var imageAddress: File? = null

    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        backToHomeAddClient.setOnClickListener {
            findNavController().navigateUp()
        }

        sendClientData.setOnClickListener {
            inputMarketName = marketName.text.toString()
            repsonsiblePerson = responsiblePerson.text.toString()
            phone1 = phoneNumber1.text.toString()
            phone2 = phoneNumber2.text.toString()
            if (assumptionValue.text.toString().isNotEmpty()) supposedAmount =
                assumptionValue.text.toString().toDouble()

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
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // request permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
                )
                //return
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

//                    latitude = location.latitude
//                    longitude = location.longitude
                    requireActivity().showToast("${location.latitude}")
                }
                .addOnFailureListener {
                    requireActivity().showToast("Fail")
                }

        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                } else {
                    // permission denied
                    requireContext().showToast("You need to grant permission to access location")
                }
            }
        }
    }
}