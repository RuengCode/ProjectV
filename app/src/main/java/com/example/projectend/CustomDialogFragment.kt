package com.example.projectend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class CustomDialogFragment : DialogFragment() {
    override fun onCreateView(inflater : LayoutInflater,container : ViewGroup?,savedInstanceState : Bundle?): View {
        var view = inflater.inflate(R.layout.fragment_custom_dialog,container,false)
        return view
    }
}