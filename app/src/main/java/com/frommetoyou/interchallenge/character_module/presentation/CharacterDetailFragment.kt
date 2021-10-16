package com.frommetoyou.interchallenge.character_module.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.character_module.adapters.ComicAdapter
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.databinding.FragmentCharacterDetailBinding


class CharacterDetailFragment : Fragment() {
    private lateinit var mBinding: FragmentCharacterDetailBinding
    val mViewModel: CharactersViewModel by lazy {
        obtainViewModel(requireActivity(),
            CharactersViewModel::class.java,
            CharactersViewModelProviderFactory(CharactersRepository()))
    }
    private lateinit var mComicAdapter: ComicAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()

    }

    private fun setupViewModel() {
        mViewModel.character.observe(viewLifecycleOwner){ character ->
            mBinding.tvDescription.text = character.description.ifEmpty { resources.getString(R.string.detail_desc_doesnt_exists) }
            mViewModel.setToolbarTitle(character.name.uppercase())
            Glide.with(requireContext())
                .load("${character.thumbnail.path.replace("http","https")}.${character.thumbnail.extension}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.ivPhoto)
            mComicAdapter.submitList(character.comics.items)
        }
    }

    private fun setupRecyclerView(){
        mComicAdapter = ComicAdapter()
        mBinding.recyclerView.apply {
            adapter = mComicAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mViewModel.setToolbarTitle(resources.getString(R.string.app_name))
    }

    fun <T : ViewModel> obtainViewModel(owner: ViewModelStoreOwner,
                                        viewModelClass: Class<T>,
                                        viewmodelFactory: CharactersViewModelProviderFactory
    ) =
        ViewModelProvider(owner, viewmodelFactory).get(viewModelClass)
}