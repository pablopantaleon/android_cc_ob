package com.example.androidccforob.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidccforob.databinding.FragmentProfileBottomSheetBinding
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.domain.usecase.UseCaseResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

	private var _binding: FragmentProfileBottomSheetBinding? = null
	private val binding get() = _binding!!

	// view-models
	private val userViewModel: UserViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeLiveEvents()
	}

	/**
	 * Subscribe to live events
	 * 1. Is User logged in?
	 * 2. Get all food items
	 * 3. Get User Data
	 */
	private fun observeLiveEvents() {
		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				//
				// observe auth state changes
				launch {
					userViewModel.userState.collect { result ->
						if (result is UseCaseResult.Succeed) {
							Glide.with(binding.ivImageProfile)
								.load(result.data.avatarUrl)
								.centerCrop()
								.apply(RequestOptions.circleCropTransform())
								.into(binding.ivImageProfile)
							binding.edName.setText(result.data.name)
							binding.edCity.setText(result.data.city)
							binding.edBio.setText(result.data.bio)
						}
					}
				}
			}
		}
	}

	companion object {

		const val TAG = "BottomSheetProfileFragment"
	}
}