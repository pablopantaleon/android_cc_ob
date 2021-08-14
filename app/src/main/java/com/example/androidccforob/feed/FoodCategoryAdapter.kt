package com.example.androidccforob.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.androidccforob.R
import com.example.androidccforob.databinding.ListItemCategoryBinding

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 13/08/21.
 */
class FoodCategoryAdapter(
	private val values: List<FoodCategoryListItem>,
	private val onItemClick: (categoryId: String) -> Unit
) : RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder>() {

	private var previousSelectedPosition: Int = 0

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			ListItemCategoryBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = values[position]
		val isSelected = position == previousSelectedPosition || item.isSelected
		holder.tvTitle.text = item.name
		holder.ivBottomLine.isVisible = isSelected

		// set text color-state depending of the selected flag
		if (isSelected) {
			val color = ContextCompat.getColor(holder.tvTitle.context, R.color.text_color)
			holder.tvTitle.setTextColor(color)
		} else {
			val color = ContextCompat.getColor(holder.tvTitle.context, R.color.hint_color)
			holder.tvTitle.setTextColor(color)
		}

		holder.root.setOnClickListener {
			onItemClick.invoke(item.name)

			// refresh item
			item.isSelected = true
			notifyItemChanged(position)

			// refresh old item
			values[previousSelectedPosition].isSelected = false
			notifyItemChanged(previousSelectedPosition)
			previousSelectedPosition = position
		}
	}

	override fun getItemCount(): Int = values.size

	inner class ViewHolder(binding: ListItemCategoryBinding) :
		RecyclerView.ViewHolder(binding.root) {

		val root: View = binding.root
		val tvTitle: TextView = binding.tvTitle
		val ivBottomLine: ImageView = binding.ivBottomLine
	}
}