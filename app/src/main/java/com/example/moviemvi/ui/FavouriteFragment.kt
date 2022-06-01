package com.example.moviemvi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.moviemvi.MainActivity
import com.example.moviemvi.R
import com.example.moviemvi.adapter.AdapterFavRv
import com.example.moviemvi.databinding.FragmentFavouriteBinding
import com.example.moviemvi.models.room_data_base.app_data_base.AppDatabase
import com.example.moviemvi.models.room_data_base.entity.MoveNewPlayingEntity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class favouriteFragment : Fragment(R.layout.fragment_favourite), CoroutineScope {
    private val binding by viewBinding(FragmentFavouriteBinding::bind)

    private lateinit var job: Job
    private lateinit var adapter: AdapterFavRv
    private lateinit var appDatabase: AppDatabase
    private var tempList = ArrayList<MoveNewPlayingEntity>()
    private var newList = ArrayList<MoveNewPlayingEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serchView()
        loadData()


    }

    private fun serchView() {
        binding.toolbarLayout.apply {

            search.setOnSearchClickListener {
                titleToolbar.visibility = View.GONE
            }

            search.setOnCloseListener(object :
                android.widget.SearchView.OnCloseListener,
                SearchView.OnCloseListener {
                override fun onClose(): Boolean {

                    titleToolbar.visibility = View.VISIBLE
                    return false
                }

            })

            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    tempList.clear()

                    val searchText = newText?.lowercase(Locale.getDefault())
                    if (searchText != null && searchText.isNotEmpty()) {
                        newList.forEach {
                            if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
                                tempList.add(it)
                            }
                        }
                        binding.rv.adapter?.notifyDataSetChanged()
                    } else {
                        tempList.clear()
                        tempList.addAll(newList)
                        binding.rv.adapter?.notifyDataSetChanged()
                    }
                    return false
                }

            })
        }

    }

    private fun loadData() {

        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()

        appDatabase = AppDatabase.getInstance(requireContext())
        job = Job()

        launch {
            tempList =
                appDatabase.moveDao().getNewMovePlayingFav(true) as ArrayList<MoveNewPlayingEntity>
            val movePopularFav = appDatabase.moveDao().getMovePopularFav(true)

            movePopularFav.forEach {
                tempList.add(
                    MoveNewPlayingEntity(
                        it.id,
                        it.title,
                        it.description,
                        it.image_url,
                        it.release_date,
                        it.rank, it.favrorite
                    )
                )
            }
            newList.clear()
            newList.addAll(tempList)

            adapter = AdapterFavRv(tempList) {
                val bundle = Bundle()
                bundle.putSerializable("favrorit", it)
                findNavController().navigate(R.id.infoPageFragment, bundle, navOptions)
            }
            if (newList.isEmpty()) {
                binding.notData.visibility = View.VISIBLE
            }
            binding.rv.adapter = adapter
        }

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onStart() {
        super.onStart()
        binding.toolbarLayout.apply {
            search.isIconified = true
            search.onActionViewCollapsed()
        }
        val mainActivity = activity as MainActivity
        mainActivity.viewVisiblite()
    }
}