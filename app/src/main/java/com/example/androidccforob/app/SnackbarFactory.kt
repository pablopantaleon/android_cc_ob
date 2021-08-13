package com.example.androidccforob.app

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.androidccforob.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
object SnackbarFactory {

	/**
	 * Create [Snackbar] for error message
	 * @param view The view to find a parent from.  This view is also used to find the anchor view
	 * when calling {@link Snackbar#setAnchorView(int)}.
	 * @param message The text to show. Can be formatted text.
	 */
	fun createErrorMessage(view: View, message: String): Snackbar {
		val errorColor = view.context.resolveColorAttr(R.attr.colorError)
		val textColor = ContextCompat.getColor(view.context, android.R.color.white)

		return Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
			setBackgroundTint(errorColor)
			setTextColor(textColor)
		}
	}

	/**
	 * Create [Snackbar] for default error message.  The default error message is retrieved from the
	 * [StringProvider] ([GENERIC_ERROR])
	 * @param view The view to find a parent from.  This view is also used to find the anchor view
	 * when calling {@link Snackbar#setAnchorView(int)}.
	 * @return null if the default error message is missing
	 */
	fun createDefaultError(view: View): Snackbar? {
		return view.context?.getString(R.string.default_error)?.let { errorMessage ->
			createErrorMessage(view, errorMessage)
		}
	}
}

@ColorInt
internal fun Context.resolveColorAttr(@AttrRes colorAttr: Int): Int {
	val resolvedAttr = resolveThemeAttr(colorAttr)
	// resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
	val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
	return ContextCompat.getColor(this, colorRes)
}

internal fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
	val typedValue = TypedValue()
	theme.resolveAttribute(attrRes, typedValue, true)
	return typedValue
}