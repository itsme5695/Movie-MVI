package com.example.moviemvi.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviemvi.R
import com.example.moviemvi.adapters.SAdapter
import com.example.moviemvi.databinding.FragmentBookmarkBinding
import com.example.moviemvi.network.ApiClient
import com.example.moviemvi.repository.MovieRepository
import com.example.moviemvi.repository.room.AppDatabase
import com.example.moviemvi.repository.room.entity.Movie
import com.example.moviemvi.view.contains.ContainsFragment
import com.example.moviemvi.viewModels.MovieViewModel
import com.example.moviemvi.viewModels.MovieViewModelFactory
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class BookmarkFragment : ContainsFragment(R.layout.fragment_bookmark) {

    private val binding by viewBinding(FragmentBookmarkBinding::bind)
    private lateinit var adapter: SAdapter
    private lateinit var viewModel: MovieViewModel
    private lateinit var appDatabase: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getInstance(requireContext())

        val repository = MovieRepository(ApiClient.getApiService, appDatabase.appDao())

        val viewModelFactory = MovieViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

        adapter = SAdapter(listener)

        binding.recyclerView.adapter = adapter

        viewModel.getAllMoviesFromDb().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private val listener = object : SAdapter.OnClickListener {
        override fun onItemClick(movie: Movie) {
            val bundle = Bundle()
            bundle.putString("movieId", movie.imdbId)
            findNavController().navigate(R.id.blankFragment, bundle)
        }
    }

}