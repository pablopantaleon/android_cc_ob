package com.example.androidccforob.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidccforob.R
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.domain.usecase.UseCaseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : Fragment() {

	private val userViewModel: UserViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_splash, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		lifecycleScope.launch {
			userViewModel.isLoggedInState
				.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
				.collect { result ->
					when (result) {
						is UseCaseResult.Initial,
						is UseCaseResult.Loading -> {
							/* NOOP */
						}
						is UseCaseResult.Succeed -> {
							if (result.data) { // is logged in
								findNavController().navigate(R.id.action_splashFragment_to_foodFeedFragment)
							} else {
								findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
							}
						}
						is UseCaseResult.Failed -> {
							findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
						}
					}
				}
		}
	}
}