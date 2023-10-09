package ntnu.katarzsz.oving4

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : FragmentActivity(), ListFragment.OnListItemClickListener {
    private val viewModel: MyViewModel by viewModels()
    val listFragment = ListFragment()
    val pictureFragment = PictureFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.games.add(Game("Destiny 2", R.drawable.destiny2, "Destiny 2 is a free-to-play online first-person shooter video game developed by Bungie. Players assume the role of a Guardian, protectors of Earth's last safe city as they wield a power called Light to protect humanity from different alien races and combat the looming threat of the Darkness"))
        viewModel.games.add(Game("Bloodborne", R.drawable.bloodborne, "Bloodborne follows the player's character, a Hunter, through the decrepit Gothic, Victorian-era–inspired city of Yharnam, whose inhabitants are afflicted with a blood-borne disease which transforms the residents, called Yharnamites, into horrific beasts. "))
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
        Log.e("EVENT","onListItemClick in MainActivity")

        pictureFragment.showPicture(position)
    }

    //positional dritt, kommer tilbake til det
    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setOrientation(newConfig)
    }*/

    /*private fun setOrientation(config: Configuration) {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.content, ListFragment())
        } else {
            transaction.replace(R.id.content, PictureFragment())
        }
        transaction.commit()
    }*/

}
