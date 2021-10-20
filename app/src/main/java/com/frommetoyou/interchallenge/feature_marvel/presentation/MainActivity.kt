package com.frommetoyou.interchallenge.feature_marvel.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.FacebookBuilder
import com.firebase.ui.auth.IdpResponse
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null
    private val mViewModel: CharactersViewModel by viewModels()

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
                        .setTosAndPrivacyPolicyUrls(resources.getString(R.string.auth_tos), resources.getString(R.string.auth_privacy))
                        .setAuthMethodPickerLayout
                            (
                            AuthMethodPickerLayout
                                .Builder(R.layout.custom_login)
                                .setEmailButtonId(R.id.btnDefaultEmail)
                                .setFacebookButtonId(R.id.btnFacebook)
                                .setTosAndPrivacyPolicyId(R.id.tvPolicy)
                                .build())
                        .setTheme(R.style.SignInTheme)
                        .setAvailableProviders(
                            listOf(
                                IdpConfig.EmailBuilder().build(),
                                FacebookBuilder().build()
                            )
                        )
                        .build()
                )
        }
    }

    private val authResultLauncher =
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

