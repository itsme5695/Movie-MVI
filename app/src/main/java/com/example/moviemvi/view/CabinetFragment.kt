package com.example.moviemvi.view

import android.os.Bundle
import android.view.View
import com.example.moviemvi.R
import com.example.moviemvi.databinding.FragmentCabinetBinding
import com.example.moviemvi.view.contains.ContainsFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class CabinetFragment : ContainsFragment(R.layout.fragment_cabinet) {

    private val binding by viewBinding(FragmentCabinetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}