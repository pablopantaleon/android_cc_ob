package com.example.androidccforob.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.androidccforob.R
import com.example.androidccforob.app.SnackbarFactory
import com.example.androidccforob.databinding.FragmentLogInBinding
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.domain.usecase.UseCaseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LogInFragment : Fragment() {

	private val userViewModel: UserViewModel by viewModels()
	private var _binding: FragmentLogInBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLogInBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpLogInForm()
		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				//
				// observe auth state changes
				launch {
					userViewModel.isLoggedInState.collect { result ->
						// user is logged in
						if (result is UseCaseResult.Succeed && result.data) {
							findNavController().navigate(R.id.action_logInFragment_to_foodFeedFragment)
						}
					}
				}
				//
				// draw the proper UI in order to reflect the current state
				launch {
					userViewModel.logInState.collect { result ->
						when (result) {
							is UseCaseResult.Failed -> {
								changeViewState(true)
								SnackbarFactory.createErrorMessage(
									binding.root,
									result.error.message ?: ""
								).show()
							}
							is UseCaseResult.Loading -> {
								changeViewState(false)
							}
							else -> {
								changeViewState(true)
							}
						}
					}
				}
				//
				// Validate if all fields fill the requirements
				launch {
					userViewModel.logInFormValidationState.collect { result ->
						when {
							result.usernameError -> {
								binding.edUsername.error = getString(R.string.invalid_username)
								binding.btnLogin.isEnabled = false
							}
							result.passwordError -> {
								binding.edPassword.error = getString(R.string.invalid_password)
								binding.btnLogin.isEnabled = false
							}
							result.hasValidData != null -> {
								binding.btnLogin.isEnabled = true
							}
							else -> {
								binding.btnLogin.isEnabled = false
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This view has two states: Normal state and Loading (trying to log in),
	 * while the request is in process the loading state is enable; normal state otherwise
	 */
	private fun changeViewState(enable: Boolean) {
		binding.btnLogin.isVisible = enable
		binding.edUsername.isEnabled = enable
		binding.edPassword.isEnabled = enable
	}

	private fun setUpLogInForm() {
		binding.run {
			val afterTextChangedListener = object : TextWatcher {
				override fun beforeTextChanged(
					s: CharSequence, start: Int, count: Int, after: Int
				) {
					// NOOP
				}

				override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
					// NOOP
				}

				override fun afterTextChanged(s: Editable) {
					userViewModel.onLogInDataChanged(
						edUsername.text.toString(),
						edPassword.text.toString()
					)
				}
			}
			edUsername.addTextChangedListener(afterTextChangedListener)
			edPassword.addTextChangedListener(afterTextChangedListener)
			edPassword.setOnEditorActionListener { _, actionId, _ ->
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					userViewModel.logIn(edUsername.text.toString(), edPassword.text.toString())
				}
				false
			}
			btnLogin.setOnClickListener {
				userViewModel.logIn(edUsername.text.toString(), edPassword.text.toString())
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}