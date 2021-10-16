package com.frommetoyou.interchallenge.feature_marvel

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null
    val mViewModel: CharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupAuth()
        setupBottomNav()
        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel.toolbarTitle.observe(this){
            mBinding.toolbar.title = it
        }
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
                        .setTheme(R.style.SignInTheme)
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
        val navController = findNavController(R.id.hostFragment)
        mBinding.bottomNav.setupWithNavController(navController)
    }
}

