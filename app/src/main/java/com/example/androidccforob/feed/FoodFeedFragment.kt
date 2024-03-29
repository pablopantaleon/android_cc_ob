package com.example.androidccforob.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.androidccforob.R
import com.example.androidccforob.app.SnackbarFactory
import com.example.androidccforob.databinding.FragmentFoodFeedBinding
import com.example.androidccforob.profile.ProfileBottomSheetFragment
import com.example.androidccforob.viewmodel.FeedViewModel
import com.example.androidccforob.viewmodel.UserViewModel
import com.example.core.UseCaseResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FoodFeedFragment : Fragment() {

	private var _binding: FragmentFoodFeedBinding? = null
	private val binding get() = _binding!!

	// view-models
	private val userViewModel: UserViewModel by viewModels()
	private val feedViewModel: FeedViewModel by viewModels()

	// adapters
	private lateinit var foodFeedAdapter: FoodFeedAdapter

	// util
	@Inject
	lateinit var feedDataAdapter: FeedDataAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentFoodFeedBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeLiveEvents()
		binding.ivImageProfile.setOnClickListener {
			val fragment = ProfileBottomSheetFragment()
			fragment.show(parentFragmentManager, ProfileBottomSheetFragment.TAG)
		}
	}

	/**
	 * Subscribe to live events
	 * 1. Is User logged in?
	 * 2. Get all food items
	 * 3. Get User Data
	 * 4. Update liked state
	 */
	private fun observeLiveEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				//
				// draw the proper UI in order to reflect the current state
				launch {
					feedViewModel.allFoodItemsState
						.onEach { result ->
							when (result) {
								is UseCaseResult.Failed -> {
									binding.pbLoading.isVisible = false
									SnackbarFactory.createErrorMessage(
										binding.root,
										getString(R.string.default_error)
									).show()
								}
								is UseCaseResult.Loading -> {
									binding.pbLoading.isVisible = true
								}
								is UseCaseResult.Success -> {
									// setup Food Adapter
									val foods = feedDataAdapter.getFeedFoodData(result.data)
									foodFeedAdapter = FoodFeedAdapter(foods) { foodId, liked ->
										feedViewModel.updateFoodLikeState(foodId, liked)
									}
									binding.pbLoading.isVisible = false
									binding.rvFeedFood.adapter = foodFeedAdapter
									// setup Food Category Adapter
									val categories =
										feedDataAdapter.getFeedFoodCategories(result.data)
									binding.rvCategories.adapter =
										FoodCategoryAdapter(categories) { filter ->
											val filteredIds =
												feedViewModel.onFilterChanged(filter, result)
											val filteredFoods =
												foods.filter { it.id in filteredIds }
											foodFeedAdapter.update(filteredFoods)
										}
								}
								else -> binding.pbLoading.isVisible = false
							}
						}.launchIn(this)
					//
					// observe auth state changes
					userViewModel.userState
						.onEach { result ->
							if (result is UseCaseResult.Success) {
								Glide.with(binding.ivImageProfile)
									.load(result.data.avatarUrl)
									.centerCrop()
									.apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
									.into(binding.ivImageProfile)
							}
						}.launchIn(this)
					//
					// Observe food data liked state changes
					feedViewModel.updateFoodLikedState
						.onEach { result ->
							if (result is UseCaseResult.Success) {
								if (result.data.success) {
									// NOOP
									// This is expected and means that food item was updated correctly
								} else if (::foodFeedAdapter.isInitialized) {
									// Update liked state has failed then restore previous UI state
									foodFeedAdapter.restoreFoodLikedState(
										result.data.foodId,
										result.data.liked
									)
									SnackbarFactory.createErrorMessage(
										binding.root,
										getString(R.string.default_error)
									).show()
								}
							}
						}.launchIn(this)
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}