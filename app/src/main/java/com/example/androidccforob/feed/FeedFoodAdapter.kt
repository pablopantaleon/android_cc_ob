package com.example.androidccforob.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidccforob.R
import com.example.androidccforob.databinding.ListItemFoodFeedBinding
import com.example.domain.entity.Food

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
class FeedFoodAdapter(
	private val values: List<Food>
) : RecyclerView.Adapter<FeedFoodAdapter.ViewHolder>() {

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
		val item = values[position]
		Glide.with(holder.ivLogo).load(item.imageUrl).into(holder.ivLogo)
		holder.tvTitle.text = item.name
		holder.tvPrice.text = holder.tvPrice.context.getString(R.string.food_feed_price, item.price)
	}

	override fun getItemCount(): Int = values.size

	inner class ViewHolder(binding: ListItemFoodFeedBinding) :
		RecyclerView.ViewHolder(binding.root) {

		val ivLogo: ImageView = binding.ivLogo
		val tvTitle: TextView = binding.tvTitle
		val tvPrice: TextView = binding.tvPrice
	}
}