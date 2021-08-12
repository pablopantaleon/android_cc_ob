package com.example.androidccforob.app

import android.app.Application
import com.example.androidccforob.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@HiltAndroidApp
class FoodApp : Application() {

	override fun onCreate() {
		super.onCreate()

		if (BuildConfig.DEBUG) {
			Timber.plant(DebugTree())
		}
	}
}