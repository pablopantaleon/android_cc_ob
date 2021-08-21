package com.example.androidccforob

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.androidccforob.login.SessionState
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.core.UseCaseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val userViewModel: UserViewModel by viewModels()
	private var sessionState: SessionState = SessionState.Idle

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
		val navController = navHostFragment.navController

		lifecycleScope.launch {
			userViewModel.isLoggedInState
				.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
				.collect { result ->
					when (result) {
						is UseCaseResult.Initial,
						is UseCaseResult.Loading -> {
							/* NOOP */
						}
						is UseCaseResult.Success -> {
							when {
								// is logged in || log in success
								result.data -> {
									// App just started
									if (sessionState is SessionState.Idle) {
										navController.navigate(R.id.action_splashFragment_to_foodFeedFragment)
									// A login success
									} else {
										navController.navigate(R.id.action_logInFragment_to_foodFeedFragment)
									}
									sessionState = SessionState.LoggedIn
								}
								// App just started
								sessionState is SessionState.Idle -> {
									navController.navigate(R.id.action_splashFragment_to_logInFragment)
									sessionState = SessionState.NotLoggedIn
								}
								// User has logged out
								sessionState is SessionState.LoggedIn -> {
									val navOptions = NavOptions.Builder()
									navOptions.setPopUpTo(R.id.splashFragment, true)
									navOptions.setLaunchSingleTop(true)
									navController.navigate(
										R.id.action_foodFeedFragment_to_logInFragment,
										null,
										navOptions.build()
									)
									sessionState = SessionState.NotLoggedIn
								}
							}
						}
						is UseCaseResult.Failed -> {
							sessionState = SessionState.NotLoggedIn
							navController.navigate(R.id.action_splashFragment_to_logInFragment)
						}
					}
				}
		}
	}
}