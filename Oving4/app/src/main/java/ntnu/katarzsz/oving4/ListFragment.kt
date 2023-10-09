package ntnu.katarzsz.oving4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ListFragment : Fragment() {
    private var mListener: OnListItemClickListener? = null
    lateinit var adapter: ArrayAdapter<Game>
    private val viewModel: MyViewModel by activityViewModels()

    interface OnListItemClickListener {
        fun onListItemClick(position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = try {
            activity as OnListItemClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity must implement OnFragmentInteractionListener"
            )
        }
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        val listView = view.findViewById<ListView>(R.id.list_of_games)

        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.games
        )

        listView.adapter = adapter

        //adapter.notifyDataSetChanged()

        listView.setOnItemClickListener { _, _, position, _ ->
            mListener?.onListItemClick(position)
        }

        return view

    }





}