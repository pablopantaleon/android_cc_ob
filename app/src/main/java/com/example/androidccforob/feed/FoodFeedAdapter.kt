package com.example.androidccforob.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidccforob.R
import com.example.androidccforob.databinding.ListItemFoodFeedBinding
import com.example.domain.entity.Food
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
@ExperimentalCoroutinesApi
class FoodFeedAdapter(
	private var values: List<FoodListItem>,
	private val onFavoriteClick: (foodId: String, liked: Boolean) -> Unit,
) : RecyclerView.Adapter<FoodFeedAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			ListItemFoodFeedBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val food = values[position]
		Glide.with(holder.ivLogo).load(food.imageUrl).into(holder.ivLogo)
		holder.tvTitle.text = food.name
		holder.tvPrice.text = holder.tvPrice.context.getString(R.string.food_feed_price, food.price)
		holder.tvRating.text = holder.tvRating.context
			.getString(R.string.food_feed_rating, food.rating)

		// draw the respective fav icon depending of the liked state
		if (food.isLiked) {
			val icon = holder.ivFavoriteIcon
			Glide.with(icon).load(R.drawable.ic_favourite_selected).into(icon)
			val background = holder.ivFavoriteBackground
			Glide.with(background).load(R.drawable.ic_favourite_selected_bkg).into(background)
		} else {
			val icon = holder.ivFavoriteIcon
			Glide.with(icon).load(R.drawable.ic_favourite_not_selected).into(icon)
			val background = holder.ivFavoriteBackground
			Glide.with(background).load(R.drawable.ic_favourite_not_bkg).into(background)
		}

		// change liked state and refresh view
		holder.root.setOnClickListener {
			val newState = !food.isLiked
			onFavoriteClick.invoke(food.id, newState)
			food.isLiked = newState
			notifyItemChanged(position)
		}
	}

	override fun getItemCount(): Int = values.size

	/**
	 * Update food liked state with a new liked state.
	 * This method is supposed to be used in case of the remote update liked state fails.
	 *
	 * @param foodId: ID of the [Food] element
	 * @param liked: The new liked state value
	 */
	fun restoreFoodLikedState(foodId: String, liked: Boolean) {
		val foodPosition: Int = values.indexOfFirst { it.id == foodId }

		// if food was found
		if (foodPosition >= 0) {
			values[foodPosition].isLiked = liked
			notifyItemChanged(foodPosition)
		}
	}

	fun update(food: List<FoodListItem>) {
		this.values = food
		notifyDataSetChanged()
	}

	inner class ViewHolder(binding: ListItemFoodFeedBinding) :
		RecyclerView.ViewHolder(binding.root) {

		val root: View = binding.root
		val ivLogo: ImageView = binding.ivLogo
		val tvTitle: TextView = binding.tvTitle
		val tvPrice: TextView = binding.tvPrice
		val tvRating: TextView = binding.tvRating
		val ivFavoriteIcon: ImageView = binding.ivIcFavorite
		val ivFavoriteBackground: ImageView = binding.ivFavorite
	}
}