package com.frommetoyou.interchallenge.feature_marvel.presentation.events_module.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Resource
import com.frommetoyou.interchallenge.databinding.FragmentEventsBinding
import com.frommetoyou.interchallenge.feature_marvel.presentation.CharactersViewModel
import com.frommetoyou.interchallenge.feature_marvel.presentation.events_module.adapters.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment() {
    private lateinit var mBinding: FragmentEventsBinding
    private val mViewModel: CharactersViewModel by activityViewModels()
    private lateinit var mEventsAdapter: EventsAdapter
    private val TAG = "EventsFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentEventsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

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
        mBinding.progressBar.visibility = View.VISIBLE
    }
}