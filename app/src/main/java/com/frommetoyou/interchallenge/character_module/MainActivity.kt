package com.frommetoyou.interchallenge.character_module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.frommetoyou.interchallenge.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.character_module.presentation.CharactersViewModel
import com.frommetoyou.interchallenge.character_module.presentation.CharactersViewModelProviderFactory
import com.frommetoyou.interchallenge.core.repository.CharactersRepository


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupAuth()
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener(mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth?.removeAuthStateListener(mAuthListener)
    }

    private fun setupAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user == null)
                authResultLauncher.launch(
                    AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            listOf(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.FacebookBuilder().build()
                            )
                        )
                        .build()
                )
        }
    }

    val authResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK)
                Snackbar.make(
                    mBinding.root,
                    resources.getString(R.string.main_welcome),
                    Snackbar.LENGTH_SHORT
                ).show()
            else
                if (IdpResponse.fromResultIntent(result.data) == null) {
                    finish()
                }
        }

    private fun setupBottomNav(){
        mBinding.bottomNav.itemIconTintList = null
        mBinding.bottomNav.setupWithNavController(findNavController(R.id.hostFragment))
    }
}