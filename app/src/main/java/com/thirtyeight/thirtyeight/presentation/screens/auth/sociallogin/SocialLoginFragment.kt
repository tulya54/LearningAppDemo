package com.thirtyeight.thirtyeight.presentation.screens.auth.sociallogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.ActivityNavigator
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nikoloz14.myextensions.showToast
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentSocialLoginBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseFragment
import timber.log.Timber

/**
 * Created by nikolozakhvlediani on 4/6/21.
 */
class SocialLoginFragment : BaseFragment<FragmentSocialLoginBinding>() {

    override val layoutRes: Int = R.layout.fragment_social_login

    private var callbackManager: CallbackManager? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun createBinding(view: View) =
            FragmentSocialLoginBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginWithFacebookContainer.setOnClickListener {
            binding.facebookLoginButton.performClick()
        }
        binding.loginWithGoogleContainer.setOnClickListener {
            signInWithGoogle()
        }
        binding.signInButton.setOnClickListener {

        }
        binding.forgotPassword.setOnClickListener {

        }
        setUpGoogleLogin()
        setUpFacebookLogin()
    }

    private fun setUpGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun setUpFacebookLogin() {
        with(binding) {
            binding.facebookLoginButton.fragment = this@SocialLoginFragment
            callbackManager = CallbackManager.Factory.create()
            facebookLoginButton.setPermissions("public_profile", "email")
            facebookLoginButton.registerCallback(
                    callbackManager,
                    object : FacebookCallback<LoginResult> {

                        override fun onSuccess(result: LoginResult?) {
                            val token = result?.accessToken?.token
                            Timber.d(token)
                            LoginManager.getInstance().logOut()
                            token?.let {
                                Timber.d("Token $it")
                                context.showToast("Hello $it")
                                navigateToApp()
                            }
                        }

                        override fun onCancel() {}

                        override fun onError(error: FacebookException?) {}
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            showToast(account?.displayName)
            googleSignInClient.signOut()
            navigateToApp()
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private fun navigateToApp() {
        val extras = ActivityNavigator.Extras.Builder()
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .build()
    }

    companion object {

        private const val RC_GOOGLE_SIGN_IN = 342
    }
}