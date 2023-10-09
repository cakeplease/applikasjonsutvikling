package ntnu.katarzsz.oving4

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : FragmentActivity(), ListFragment.OnListItemClickListener {
    private val viewModel: MyViewModel by viewModels()
    val listFragment = ListFragment()
    val pictureFragment = PictureFragment()
    lateinit var mainLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.games.add(Game("Destiny 2", R.drawable.destiny2, "Destiny 2 is a free-to-play online first-person shooter video game developed by Bungie. Players assume the role of a Guardian, protectors of Earth's last safe city as they wield a power called Light"))
        viewModel.games.add(Game("Bloodborne", R.drawable.bloodborne, "Bloodborne follows the player's character, a Hunter, through the decrepit Gothic, Victorian-era–inspired city of Yharnam, whose inhabitants are afflicted with a blood-borne disease which transforms the residents, called Yharnamites, into horrific beasts."))
        viewModel.games.add(Game("Final Fantasy XIV Online", R.drawable.ffxiv, "Final Fantasy XIV is set in the fantasy region of Eorzea, five years after the devastating Seventh Umbral Calamity which ended the original version."))
        //TODO add more games

        supportFragmentManager.beginTransaction()
            .add(R.id.content, listFragment)
            .add(R.id.content, pictureFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val previous = menu.add("Previous")
        val next = menu.add("Next")

        previous.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        next.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title.toString()) {
            "Previous" -> pictureFragment.showPreviousPicture()
            "Next" -> pictureFragment.showNextPicture()
        }
        return true
    }

    override fun onListItemClick(position: Int) {
        pictureFragment.showPicture(position)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setOrientation(newConfig)
    }

    //TODO add more responsive layout
    private fun setOrientation(config: Configuration) {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mainLayout = findViewById(R.id.content)
            mainLayout.orientation = LinearLayout.VERTICAL
        } else {
            mainLayout = findViewById(R.id.content)
            mainLayout.orientation = LinearLayout.HORIZONTAL
        }
        transaction.commit()
    }

}
