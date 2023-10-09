package ntnu.katarzsz.oving4

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class PictureFragment : Fragment() {
    private val viewModel: MyViewModel by activityViewModels()
    var currentIndex = -1
    lateinit var imageView: ImageView
    lateinit var description: TextView
    lateinit var title: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.picture_fragment, container, false)
        imageView = view.findViewById(R.id.image)
        description = view.findViewById(R.id.current_description)
        title = view.findViewById(R.id.game_title)
        return view
    }

    fun showPicture(position: Int) {
        //Log.e("test", "Showing picture number: ${viewModel.games[position].getPicturePath()}")
        val bitmap = BitmapFactory.decodeResource(resources, viewModel.games[position].getPicturePath())
        imageView.setImageBitmap(bitmap)
        description.text = viewModel.games[position].getDescription()
        title.text = viewModel.games[position].getTitle()
        currentIndex = position
    }

    fun showPreviousPicture() {
        Log.e("Current game index", currentIndex.toString())
        if (currentIndex>0) {
            showPicture(currentIndex-1)
        }
    }
    fun showNextPicture() {
        Log.e("Current game index", currentIndex.toString())
        if (currentIndex +1 < viewModel.games.size) {
            showPicture(currentIndex+1)
        }

    }
}