package ntnu.katarzsz.oving7.managers

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import ntnu.katarzsz.oving7.R
import androidx.preference.PreferenceManager

class MyPreferenceManager(private val activity: Activity) {

	private val resources = activity.resources
	private val preferences = PreferenceManager.getDefaultSharedPreferences(activity)


	fun getString(key: String, defaultValue: String): String {
		return preferences.getString(key, defaultValue) ?: defaultValue
	}

	fun updateBackgroundColor() {
		Log.e("test","test")
		val colors =  resources.getStringArray(R.array.background_values)
		val value = getString(
				resources.getString(R.string.background_color),
				resources.getString(R.string.background_values_default_value)
		)

		val preferences: SharedPreferences = activity.getSharedPreferences("prefs", 0)
		val editor: SharedPreferences.Editor = preferences.edit()
		Log.e("value",  value)
		//editor.putString("backgroundColor", value)

		when (value) {
			colors[0] -> editor.putString("backgroundColor", colors[0].toString())
			colors[1] -> editor.putString("backgroundColor", colors[1].toString())
			colors[2] -> editor.putString("backgroundColor", colors[2].toString())
		}
		editor.apply()


	}

	fun registerListener(activity: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.registerOnSharedPreferenceChangeListener(activity)
	}

	fun unregisterListener(activity: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.unregisterOnSharedPreferenceChangeListener(activity)
	}
}
