package com.example.moviemvi.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviemvi.models.Search
import com.example.moviemvi.network.ApiService
import retrofit2.HttpException
import java.io.IOException

const val STARTING_INDEX = 1

class PagerSource(private val s: String, private val apiService: ApiService) :
    PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val position = params.key ?: STARTING_INDEX
            val response = apiService.getMovies(search = s, page = position)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()?.Search ?: emptyList(),
                    prevKey = if (position == STARTING_INDEX) null else position - 1,
                    nextKey = if (response.body()!!.Search.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Page(
                    emptyList(),
                    null,
                    position
                )
            }

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}