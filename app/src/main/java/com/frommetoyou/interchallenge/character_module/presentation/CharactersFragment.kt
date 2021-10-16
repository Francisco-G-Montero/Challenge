package com.frommetoyou.interchallenge.character_module.presentation

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.character_module.adapters.CharacterAdapter
import com.frommetoyou.interchallenge.character_module.adapters.OnClickListener
import com.frommetoyou.interchallenge.databinding.FragmentCharactersBinding
import com.frommetoyou.interchallenge.core.entities.Result
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.core.util.Resource

class CharactersFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentCharactersBinding
    val mViewModel: CharactersViewModel by lazy {
        obtainViewModel(requireActivity(),
            CharactersViewModel::class.java,
            CharactersViewModelProviderFactory(CharactersRepository()))
    }
    private lateinit var mCharactersAdapter: CharacterAdapter
    private val TAG = "CharactersFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentCharactersBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mViewModel.characters.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { characterResponse ->
                        mCharactersAdapter.submitList(characterResponse.data.results)
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
    fun <T : ViewModel> obtainViewModel(owner: ViewModelStoreOwner,
                                                 viewModelClass: Class<T>,
                                                 viewmodelFactory: CharactersViewModelProviderFactory
    ) =
        ViewModelProvider(owner, viewmodelFactory).get(viewModelClass)
    private fun setupRecyclerView(){
        mCharactersAdapter = CharacterAdapter(this)
        mBinding.recyclerView.apply {
            adapter = mCharactersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun hideProgressBar(){
        mBinding.progressBar.visibility = View.GONE
    }
    private fun showProgressBar(){
        mBinding.progressBar.visibility = View.GONE
    }

    override fun onClick(character: Result) {
        Toast.makeText(context, "Character", Toast.LENGTH_SHORT).show()
        mViewModel.setCharacterToDetail(character)
        findNavController().navigate(R.id.action_charactersFragment_to_characterDetailFragment2)
    }
}