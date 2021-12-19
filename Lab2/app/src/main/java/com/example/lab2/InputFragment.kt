package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle? ){
        super.onViewCreated(view, savedInstanceState)

        val textView1: AutoCompleteTextView = view.findViewById(R.id.editFrom)
        val textView2: AutoCompleteTextView = view.findViewById(R.id.editTo)

        val radio: RadioGroup = view.findViewById(R.id.radio)


        val cities: Array<out String> = resources.getStringArray(R.array.cities_array)
            ArrayAdapter(view.context, android.R.layout.simple_list_item_1, cities).also {  adapter ->
            textView1.setAdapter(adapter)
            textView2.setAdapter(adapter)
        }

        lateinit var cityFrom: String
        lateinit var cityTo: String
        lateinit var time: String
        val submitButton: Button = view.findViewById(R.id.button)

        submitButton.setOnClickListener{
            cityFrom = textView1.text.toString()
            cityTo = textView2.text.toString()

            val args = Bundle()
            val duration = Toast.LENGTH_SHORT

            val fragment = OutputFragment()
            fragment.arguments = args

            if (cityFrom in cities && cityTo in cities && radio.checkedRadioButtonId != -1) {
                time = view.findViewById<RadioButton>(radio.checkedRadioButtonId).text.toString()
                args.putString("text", getString(R.string.train_message, cityFrom, cityTo, time))

                val text = "Data added to DataBase"
                Toast.makeText(view.context, text, duration).show()

                childFragmentManager.beginTransaction().add(R.id.output, fragment).commit()
                submitButton.visibility = View.GONE

                view.findViewById<AutoCompleteTextView?>(R.id.editFrom)?.setText("")
                view.findViewById<AutoCompleteTextView?>(R.id.editTo)?.setText("")
                view.findViewById<RadioGroup?>(R.id.radio)?.clearCheck()

            } else {
                val text = "Wrong Data"

                val toast = Toast.makeText(view.context, text, duration)
                toast.show()
            }


            }

        }



}