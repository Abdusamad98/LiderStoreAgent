package com.example.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.source.local.TokenSaver
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment:Fragment(R.layout.profile_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        agent_firstname.text = TokenSaver.getFirstName()
        agent_lastname.text = TokenSaver.getLastName()
        agent_phone.text = TokenSaver.getLogin()
    }
}