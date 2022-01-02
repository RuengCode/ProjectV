package com.example.projectend.Fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.example.projectend.R

class LoadingFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loading_fragment, container,false)
        var progressBar : ProgressBar = view.findViewById(R.id.progrssBar)
        progressBar.max =6
        val currentProgress = 10
        ObjectAnimator.ofInt(progressBar,"progress",currentProgress)
            .setDuration(3000)
            .start()
        return view
    }
}
