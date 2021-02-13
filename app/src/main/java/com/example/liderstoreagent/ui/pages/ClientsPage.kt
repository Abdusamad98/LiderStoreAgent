package com.example.liderstoreagent.ui.pages

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.ClientsData
import com.example.liderstoreagent.ui.adapters.ClientListAdapter
import kotlinx.android.synthetic.main.clients_fragment.*

class ClientsPage : Fragment(R.layout.clients_fragment) {

    lateinit var clientAdapter: ClientListAdapter
    lateinit var data: ArrayList<ClientsData>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }

    private var countListener: ((Int) -> Unit)? = null
    fun click(f: (Int) -> Unit) {
        countListener = f
    }

    fun initRecycler() {
        clientAdapter = ClientListAdapter()
        clientAdapter.submitList(fakeData())
        recyclerClients.layoutManager = LinearLayoutManager(requireContext())
        recyclerClients.adapter = clientAdapter

        clientAdapter.setOnSellProductListener { position ->
            Toast.makeText(requireContext(), "$position", Toast.LENGTH_SHORT).show()

        }
    }

    fun fakeData(): ArrayList<ClientsData> {
        var data = ArrayList<ClientsData>()

        data.add(
            ClientsData(
                "Namekjfejfnd",
                "duewfToshkent Yunusobod",
                "Akmaljon Sobirov",
                "+998991234567",
                "0"
            )
        )




        for (x in 0..10) data.add(
            ClientsData(
                "Namekjfejfnd",
                "duewfToshkent Yunusobod",
                "Akmaljon Sobirov",
                "+998991234567",
                "125000"
            )
        )


        data.add(
            ClientsData(
                "Namekjfejfnd",
                "duewfToshkent Yunusobod",
                "Akmaljon Sobirov",
                "+998991234567",
                "0"
            )
        )

        return data
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.clients_menu, menu)
        //super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_client -> {
                Toast.makeText(requireContext(), "Developing...", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}