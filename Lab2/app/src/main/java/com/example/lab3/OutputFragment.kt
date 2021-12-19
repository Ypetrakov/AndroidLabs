    package com.example.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.example.lab2.R


    class OutputFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_output, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle? ){
        super.onViewCreated(view, savedInstanceState)
        val cancelButton: Button = view.findViewById(R.id.cancel_button)
        val outputText: TextView = view.findViewById(R.id.textView)
        val textOfOutput = arguments?.getString("text", "Wrong data")
        outputText.text = textOfOutput

        cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragment?.view?.findViewById<Button?>(R.id.button)?.visibility = View.VISIBLE

        }
    }
}