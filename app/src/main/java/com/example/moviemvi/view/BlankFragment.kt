package com.example.moviemvi.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviemvi.R
import com.example.moviemvi.databinding.FragmentBlankBinding
import com.example.moviemvi.network.ApiClient
import com.example.moviemvi.repository.MovieRepository
import com.example.moviemvi.repository.room.AppDatabase
import com.example.moviemvi.repository.room.entity.Movie
import com.example.moviemvi.resource.MovieResource
import com.example.moviemvi.view.contains.ContainsFragment
import com.example.moviemvi.viewModels.MovieViewModel
import com.example.moviemvi.viewModels.MovieViewModelFactory
import com.squareup.picasso.Picasso
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BlankFragment : ContainsFragment(R.layout.fragment_blank) {

    private var movieId: String? = null
    private var state: String? = null
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getString("movieId")
            state = it.getString("state")
        }
    }

    private val binding by viewBinding(FragmentBlankBinding::bind)
    private lateinit var viewModel: MovieViewModel
    private lateinit var appDatabase: AppDatabase
    private var checker = false

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.circularProgress.maxProgress = 10.0

        appDatabase = AppDatabase.getInstance(requireContext())

        val repository = MovieRepository(ApiClient.getApiService, appDatabase.appDao())

        val viewModelFactory = MovieViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

        checkFormDb()

        checkFromState()

        buttonClick()

        getMotionTransitionListener()

    }

    private fun getMotionTransitionListener() {
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Log.d("TAG", "onTransitionCompleted: $currentId")
                if (currentId == R.id.start) {
                    binding.tvTitle.setTextColor(Color.parseColor("#000000"))
                    binding.tvDuration.setTextColor(Color.parseColor("#000000"))
                } else {
                    binding.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvDuration.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
    }

    private fun buttonClick() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnBookmark.setOnClickListener {
            launch {
                checker = if (!checker) {
                    viewModel.insertToDb(movie)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                    true
                } else {
                    viewModel.deleteFomDb(movie)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
                    false
                }
            }
        }
    }

    private fun checkFromState() {
        if (!checker) {
            getFromNetwork()
        } else {
            Picasso.get().load(movie.poster).into(binding.contentImage)
            setComponentsTo(
                movie.title ?: "",
                movie.plot ?: "",
                movie.runtime ?: "",
                movie.sort_of_movie ?: "",
                (movie.ratings ?: 0.0) as Double,
                movie.poster ?: "",
                movie.imdbId ?: "",
                movie.language ?: ""
            )
            checker = true
        }
    }

    private fun checkFormDb() {
        launch {
            val movie1 = viewModel.getMovie(movieId!!) ?: Movie(imdbId = "11")
            checker = if (movie1.imdbId == movieId ?: "") {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                movie = movie1
                true
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
                false
            }
        }
    }


    private fun getFromNetwork() {
        launch {
            viewModel.setMovieId(movieId!!)
                .collect {
                    when (it) {
                        is MovieResource.Loading -> {
                        }
                        is MovieResource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is MovieResource.Success -> {
                            val movieModel = it.movieModel
                            val imdbID = movieModel.imdbID
                            val poster = movieModel.Poster
                            val title = movieModel.Title
                            val plot = movieModel.Plot
                            val language = movieModel.Language
                            val ratings = movieModel.Ratings[0]
                            val info = ratings.Source
                            val runtime = movieModel.Runtime
                            setComponentsTo(
                                title,
                                plot,
                                runtime,
                                info,
                                ratings.Value.toDouble(),
                                poster,
                                imdbID,
                                language
                            )
                        }
                    }
                }
        }
    }

    private fun setComponentsTo(
        tv_title: String,
        tv_plot: String,
        tv_runtime: String,
        sort_of_movie: String,
        db_rating: Double,
        url_poster: String,
        imdbId: String,
        tv_language: String
    ) {
        binding.apply {
            tvTitle.text = tv_title
            tvInfo.text = sort_of_movie
            tvDuration.text = tv_runtime
            circularProgress.setCurrentProgress(db_rating)
            tvPlot.text = tv_plot
            Picasso.get().load(url_poster).into(binding.contentImage)
            movie = Movie(
                imdbId = imdbId,
                title = tv_title,
                plot = tv_plot,
                poster = url_poster,
                imdbRating = imdbId,
                language = tv_language,
                ratings = db_rating.toString(),
                runtime = tv_runtime,
                sort_of_movie
            )
        }
    }

}