package com.example.moviemvi.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviemvi.R
import com.example.moviemvi.adapters.PagerAdapter
import com.example.moviemvi.adapters.VpAdapter
import com.example.moviemvi.databinding.FragmentMainBinding
import com.example.moviemvi.models.Search
import com.example.moviemvi.network.ApiClient
import com.example.moviemvi.repository.SourceRepository
import com.example.moviemvi.resource.SearchResource
import com.example.moviemvi.view.contains.ContainsFragment
import com.example.moviemvi.viewModels.SourceViewModel
import com.example.moviemvi.viewModels.SourceViewModelFactory
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs


class MainFragment : ContainsFragment(R.layout.fragment_main), CoroutineScope {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = ApiClient.getApiService

        listPager = ArrayList()

        handler = Handler(Looper.getMainLooper())

        val viewModelFactory = SourceViewModelFactory(SourceRepository(apiService))

        viewModel = ViewModelProvider(this, viewModelFactory)[SourceViewModel::class.java]

        adapter = PagerAdapter(onItemClickListener)

        vpAdapter = VpAdapter()

        binding.recyclerView.adapter = adapter

        connectViewPager()

        connectListViewPager()

        viewModel.getMovie.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private val onItemClickListener = object : PagerAdapter.OnClickListener {
        override fun onItemClick(search: Search) {
            val bundle = Bundle()
            bundle.putString("movieId", search.imdbID)
            bundle.putString("state", "Main")
            findNavController().navigate(R.id.blankFragment, bundle)
        }
    }

    private fun connectListViewPager() {
        launch {
            viewModel.getSearchedMovie()
                .collect {
                    when (it) {
                        is SearchResource.Loading -> {

                        }
                        is SearchResource.Error -> {

                        }
                        is SearchResource.Success -> {
                            val list = it.searchModel?.Search
                            listPager.addAll(list!!)
                            binding.viewpager2.adapter = vpAdapter
                            vpAdapter.setContent(listPager)
                        }
                    }
                }
        }
    }

    private fun connectViewPager() {
        binding.apply {
            viewpager2.clipToPadding = false
            viewpager2.clipChildren = false
            viewpager2.offscreenPageLimit = 3
            viewpager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            val pageTransformer = CompositePageTransformer()
            pageTransformer.addTransformer(MarginPageTransformer(0))
            pageTransformer.addTransformer { page, position ->
                val v = 1 - abs(position)
                page.y = 0.8f + v * 0.2f
            }
            viewpager2.setPageTransformer(pageTransformer)
            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 3000)
                }
            })
        }
    }

    private val runnable = Runnable {
        if (binding.viewpager2.currentItem == vpAdapter.itemCount - 1) {
            binding.viewpager2.currentItem = 0
        } else {
            binding.viewpager2.currentItem = binding.viewpager2.currentItem + 1
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private lateinit var listPager: ArrayList<Search>
    private lateinit var vpAdapter: VpAdapter
    private lateinit var handler: Handler
    private lateinit var adapter: PagerAdapter
    private lateinit var viewModel: SourceViewModel
    private val binding by viewBinding(FragmentMainBinding::bind)

}