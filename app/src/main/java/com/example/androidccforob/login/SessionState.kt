package com.example.androidccforob.login

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 20/08/21.
 */
sealed class SessionState {
	object Idle : SessionState()
	object LoggedIn : SessionState()
	object NotLoggedIn : SessionState()
}