package com.frommetoyou.interchallenge.events_module.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.frommetoyou.interchallenge.character_module.adapters.CharacterAdapter
import com.frommetoyou.interchallenge.character_module.adapters.ComicAdapter
import com.frommetoyou.interchallenge.character_module.presentation.CharactersViewModel
import com.frommetoyou.interchallenge.character_module.presentation.CharactersViewModelProviderFactory
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.core.util.Resource
import com.frommetoyou.interchallenge.databinding.FragmentCharacterDetailBinding
import com.frommetoyou.interchallenge.databinding.FragmentEventsBinding
import com.frommetoyou.interchallenge.events_module.adapters.EventsAdapter

class EventsFragment : Fragment() {
    private lateinit var mBinding: FragmentEventsBinding
    val mViewModel: CharactersViewModel by lazy {
        obtainViewModel(requireActivity(),
            CharactersViewModel::class.java,
            CharactersViewModelProviderFactory(CharactersRepository()))
    }
    private lateinit var mEventsAdapter: EventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentEventsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }
    private val TAG = "EventsFragment"

    private fun setupViewModel() {
        mViewModel.events.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { eventsResponse ->
                        mEventsAdapter.submitList(eventsResponse.data.results)
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

    private fun setupRecyclerView(){
        //EVENTS
        mEventsAdapter = EventsAdapter()
        mBinding.recyclerView.apply {
            adapter = mEventsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun hideProgressBar(){
        mBinding.progressBar.visibility = View.GONE
    }
    private fun showProgressBar(){
        mBinding.progressBar.visibility = View.GONE
    }

    fun <T : ViewModel> obtainViewModel(owner: ViewModelStoreOwner,
                                        viewModelClass: Class<T>,
                                        viewmodelFactory: CharactersViewModelProviderFactory
    ) =
        ViewModelProvider(owner, viewmodelFactory).get(viewModelClass)
}