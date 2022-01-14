package com.example.projectend

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth

class CustomDialogFragment : DialogFragment() {
    private lateinit var ratingRadioGroup:RadioGroup
    private lateinit var submitButton:Button

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ): View {

        var rootView = inflater.inflate(R.layout.fragment_custom_dialog,container,false)
//        ratingRadioGroup = rootView.findViewById(R.id.ratingRadioGroup)
        submitButton = rootView.findViewById(R.id.submitButton)
        submitButton.setOnClickListener{
            ratingRadioGroup = rootView.findViewById(R.id.ratingRadioGroup1)

            ratingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.excellentRadioButton1 ){
                    Toast.makeText(context,"yes",Toast.LENGTH_LONG).show()
                }
                if (checkedId == R.id.excellentRadioButton2){
                    Toast.makeText(context,"no",Toast.LENGTH_LONG).show()
                }

            }



        }
        return rootView
    }
}