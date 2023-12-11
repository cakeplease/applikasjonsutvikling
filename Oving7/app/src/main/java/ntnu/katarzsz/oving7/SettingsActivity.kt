package ntnu.katarzsz.oving7

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ntnu.katarzsz.oving7.managers.MyPreferenceManager

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener,
    Preference.SummaryProvider<ListPreference> {

    private lateinit var myPreferenceManager: MyPreferenceManager
    private lateinit var button: Button
    private lateinit var view: View

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.e("SettingsActivity", "onSharedPreferenceChanged")
        if (key == getString(R.string.background_color)){
            myPreferenceManager.updateBackgroundColor()
            val backgroundColor: String? = myPreferenceManager.getString("backgroundColor", "")

            when(backgroundColor) {
                "pink" -> view.setBackgroundColor(Color.argb(255, 255, 216, 236))
                "purple" -> view.setBackgroundColor(Color.argb(255, 219, 185, 255))
                "blue" -> view.setBackgroundColor(Color.argb(255, 185, 250, 255))
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("SettingsActivity", "onCreate")
        myPreferenceManager = MyPreferenceManager(this)
        myPreferenceManager.registerListener(this)
        setContentView(R.layout.activity_settings)
        view = findViewById<View>(android.R.id.content).rootView

        val preferences: SharedPreferences = getSharedPreferences("prefs", 0)
        val backgroundColor: String? = preferences.getString("backgroundColor", "")

        when(backgroundColor) {
            "pink" -> view.setBackgroundColor(Color.argb(255, 255, 216, 236))
            "purple" -> view.setBackgroundColor(Color.argb(255, 219, 185, 255))
            "blue" -> view.setBackgroundColor(Color.argb(255, 185, 250, 255))
        }

        button = findViewById(R.id.button)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()

        button.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun provideSummary(preference: ListPreference): CharSequence? {
        Log.e("provideSummary", preference.toString())
        return when (preference?.key) {
            getString(R.string.background_color) -> preference.entry
            else                           -> "Unknown Preference"
        }
    }

    override fun onDestroy() {
        Log.e("destroy", "destroy")
        super.onDestroy()
        myPreferenceManager.unregisterListener(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            Log.e("SettingsFragment", "onCreatePreferences")
            setPreferencesFromResource(R.xml.preference_screen, rootKey)
            Log.e("SettingsFragment", "after onCreatePreferences")
        }
    }

}