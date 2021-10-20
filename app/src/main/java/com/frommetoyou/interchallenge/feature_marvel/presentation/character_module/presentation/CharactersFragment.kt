package com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.Result
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.QUERY_CHARACTERS_PAGE_SIZE
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Resource
import com.frommetoyou.interchallenge.databinding.FragmentCharactersBinding
import com.frommetoyou.interchallenge.feature_marvel.presentation.CharactersViewModel
import com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.adapters.CharacterAdapter
import com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.adapters.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentCharactersBinding
    val mViewModel: CharactersViewModel by activityViewModels()
    private lateinit var mCharactersAdapter: CharacterAdapter
    private val TAG = "CharactersFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCharactersBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mViewModel.characters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { characterResponse ->
                        mCharactersAdapter.submitList(characterResponse.data.results.toList())
                        val totalPages = characterResponse.data.total / QUERY_CHARACTERS_PAGE_SIZE + 2
                        isLastPage = mViewModel.charactersPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(context, "Error in response!", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error in response: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        mCharactersAdapter = CharacterAdapter(this)
        mBinding.recyclerView.apply {
            adapter = mCharactersAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(scrollListener)
        }
    }

    private fun hideProgressBar() {
        mBinding.progressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        mBinding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeggining = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_CHARACTERS_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage &&
                    isAtLastItem &&
                    isNotAtBeggining &&
                    isTotalMoreThanVisible &&
                    isScrolling
            if (shouldPaginate) {
                mViewModel.getCharacters(totalItemCount)
                isScrolling = false
            }
        }
    }

    override fun onClick(character: Result) {
        mViewModel.setCharacterToDetail(character)
        findNavController().navigate(R.id.action_charactersFragment_to_characterDetailFragment2)
    }
}