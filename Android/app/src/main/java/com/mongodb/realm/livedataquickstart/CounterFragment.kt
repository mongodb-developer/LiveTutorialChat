package com.mongodb.realm.livedataquickstart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.databinding.DataBindingUtil
import com.mongodb.realm.livedataquickstart.R
import com.mongodb.realm.livedataquickstart.databinding.CounterFragmentBinding
import com.mongodb.realm.livedataquickstart.model.CounterModel
import kotlinx.android.synthetic.main.counter_fragment.view.*
import com.mongodb.realm.livedataquickstart.BR.counterModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CounterFragment : Fragment() {

    lateinit var binding:CounterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: CounterModel by viewModels()

        binding = CounterFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            counterModel = model
        }

        /*
        // Replaced by onClick listener in counter_fragment.xml
        binding.root.button.setOnClickListener {
            Log.v("QUICKSTART", "Clicked increment button. Current value: ${model.counter.value?.value?.get()}")
            model.incrementCounter()
        }
        */

        return binding.root
    }
}