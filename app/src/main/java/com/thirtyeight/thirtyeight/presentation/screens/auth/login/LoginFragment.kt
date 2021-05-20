package com.thirtyeight.thirtyeight.presentation.screens.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
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
import com.nikoloz14.myextensions.hideKeyboard
import com.nikoloz14.myextensions.showToast
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentLoginBinding
import com.thirtyeight.thirtyeight.presentation.ext.bindToInputView
import com.thirtyeight.thirtyeight.presentation.ext.textChanges
import com.thirtyeight.thirtyeight.presentation.ui.InputView
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class LoginFragment : ViewStateViewModelFragment<FragmentLoginBinding, LoginViewModel>() {

    private val inputMap = hashMapOf<Long, InputView>()
    var passwordIsShown = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private var callbackManager: CallbackManager? = null

    override val layoutRes: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()

    override fun createBinding(view: View): FragmentLoginBinding = FragmentLoginBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGoogleLogin()
        setUpFacebookLogin()
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.processUiAction(LoginUiAction.LoginClicked)
            }
            forgotPasswordButton.setOnClickListener {
                viewModel.processUiAction(LoginUiAction.ForgotPasswordClicked)
            }

            inputMap.putAll(arrayOf(
                    viewModel.emailInputId to emailInputView,
                    viewModel.passwordInputId to passwordInputView
            ))

            emailInputView.editText.textChanges()
                    .onEach { viewModel.processUiAction(LoginUiAction.InputTextChanged(viewModel.emailInputId, it.toString())) }
                    .launchIn(lifecycleScope)

            passwordInputView.editText.textChanges()
                    .onEach { viewModel.processUiAction(LoginUiAction.InputTextChanged(viewModel.passwordInputId, it.toString())) }
                    .launchIn(lifecycleScope)

            passwordInputView.onDrawableEndClicked = {
                if (passwordIsShown) {
                    passwordInputView.setDrawableEnd(R.drawable.ic_show_password)
                    passwordInputView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else {
                    passwordInputView.setDrawableEnd(R.drawable.ic_hide_password)
                    passwordInputView.inputType = (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                passwordInputView.invalidateViewState()
                passwordIsShown = !passwordIsShown
            }
            loginWithGoogleContainer.setOnClickListener {
                signInWithGoogle()
            }
            loginWithFacebookContainer.setOnClickListener {
                facebookLoginButton.performClick()
            }
        }
    }

    override fun onViewModelCreated(viewModel: LoginViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                binding.loginButton.isEnabled = loginButtonEnabled
                form.inputs.forEach { inputState ->
                    inputMap[inputState.id]?.let { inputView ->
                        inputState.bindToInputView(inputView)
                    }
                }
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            hideKeyboard()
            when (it) {
                NavigateTo.App -> {
                    navigateToApp()
                }
                NavigateTo.ForgotPassword -> {
                    val directions = LoginFragmentDirections.actionNavLoginToNavForgotPassword()
                    findNavController().navigate(directions)
                }
            }
        }
    }

    private fun setUpGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private fun setUpFacebookLogin() {
        with(binding) {
            binding.facebookLoginButton.fragment = this@LoginFragment
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

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
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
            showToast("Hello ${account!!.id}")
            navigateToApp()
            googleSignInClient.signOut()
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
        val direction = LoginFragmentDirections.actionNavLoginToNavActivityMain()
        findNavController().navigate(direction, extras)
    }

    companion object {

        private const val RC_GOOGLE_SIGN_IN = 342
    }
}