package com.example.moviemvi.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviemvi.R
import com.example.moviemvi.adapters.PagerAdapter
import com.example.moviemvi.databinding.FragmentSearchBinding
import com.example.moviemvi.models.Search
import com.example.moviemvi.network.ApiClient
import com.example.moviemvi.repository.SourceRepository
import com.example.moviemvi.view.contains.ContainsFragment
import com.example.moviemvi.viewModels.SourceViewModel
import com.example.moviemvi.viewModels.SourceViewModelFactory
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SearchFragment : ContainsFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var viewModel: SourceViewModel
    private lateinit var adapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = SourceViewModelFactory(SourceRepository((ApiClient.getApiService)))

        viewModel = ViewModelProvider(this, viewModelFactory)[SourceViewModel::class.java]

        adapter = PagerAdapter(onClickListener)

        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val str = binding.edSearch.text.toString()
            viewModel.getSearchedList(str)
        }

        viewModel.getMovie.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    private val onClickListener = object : PagerAdapter.OnClickListener {
        override fun onItemClick(search: Search) {
            val bundle = Bundle()
            bundle.putString("movieId", search.imdbID)
            bundle.putString("state", "Main")
            findNavController().navigate(R.id.blankFragment, bundle)
        }
    }
}