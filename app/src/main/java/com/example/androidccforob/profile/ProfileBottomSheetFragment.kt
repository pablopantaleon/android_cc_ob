package com.example.androidccforob.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidccforob.R
import com.example.androidccforob.app.SnackbarFactory
import com.example.androidccforob.databinding.FragmentProfileBottomSheetBinding
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.core.UseCaseResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val dialog = super.onCreateDialog(savedInstanceState)

		dialog.setOnShowListener {
			val bottomSheet = dialog.findViewById<View>(
				com.google.android.material.R.id.design_bottom_sheet
			) as FrameLayout
			val behavior = BottomSheetBehavior.from(bottomSheet)
			behavior.state = BottomSheetBehavior.STATE_EXPANDED
		}

		return dialog
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeLiveEvents()
		binding.signOut.setOnClickListener {
			userViewModel.logOut()
			dismiss()
		}
		binding.saveProfile.setOnClickListener {
			val name = binding.edName.editableText.toString()
			val city = binding.edCity.editableText.toString()
			val bio = binding.edBio.editableText.toString()
			userViewModel.updateUser(name, city, bio)
		}
	}

	/**
	 * Subscribe to live events
	 * 1. Fill up form with user data
	 * 2. Update user profile
	 */
	private fun observeLiveEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch {
					//
					// observe auth state changes
					userViewModel.userState
						.onEach { result ->
							if (result is UseCaseResult.Success) {
								Glide.with(binding.ivImageProfile)
									.load(result.data.avatarUrl)
									.centerCrop()
									.apply(RequestOptions.circleCropTransform())
									.into(binding.ivImageProfile)
								binding.edName.setText(result.data.name)
								binding.edCity.setText(result.data.city)
								binding.edBio.setText(result.data.bio)
							}
						}.launchIn(this)
					//
					// observe user updates
					userViewModel.updateUserState
						.onEach { result ->
							when (result) {
								is UseCaseResult.Success -> {
									handleUiLoadingState(false)
									SnackbarFactory.createSuccessMessage(
										binding.root,
										getString(R.string.edit_your_profile_success),
									).show()
								}
								is UseCaseResult.Failed -> {
									handleUiLoadingState(false)
									SnackbarFactory.createErrorMessage(
										binding.root,
										getString(R.string.edit_your_profile_error),
									).show()
								}
								is UseCaseResult.Loading -> handleUiLoadingState(true)
								else -> handleUiLoadingState(false)
							}
						}.launchIn(this)
				}
			}
		}
	}

	private fun handleUiLoadingState(isLoading: Boolean) {
		binding.edName.isEnabled = !isLoading
		binding.edCity.isEnabled = !isLoading
		binding.edBio.isEnabled = !isLoading
		binding.saveProfile.isEnabled = !isLoading
		binding.signOut.isEnabled = !isLoading
		binding.pbLoading.isVisible = isLoading
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	companion object {

		const val TAG = "BottomSheetProfileFragment"
	}
}