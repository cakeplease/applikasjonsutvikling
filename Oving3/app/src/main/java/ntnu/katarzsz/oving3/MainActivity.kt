package ntnu.katarzsz.oving3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView


class Friend constructor(private var name: String, private var date: String) {
    override fun toString(): String {
        return "$name $date"
    }
    fun setName(name: String) {
        this.name = name
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getName(): String {
        return this.name
    }

    fun getDate() : String {
        return this.date
    }
}

internal object RequestCode {
    const val EDIT_FRIEND = 1
}

class MainActivity : Activity() {

    lateinit var friends: ArrayList<Friend>
    lateinit var friend_list: ListView
    lateinit var registerFriendButton: Button
    lateinit var name: EditText
    lateinit var date: EditText
    lateinit var adapter: ArrayAdapter<Friend?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        friends = ArrayList()

        friend_list = findViewById(R.id.friend_list)
        registerFriendButton = findViewById(R.id.registerFriendButton)

        adapter = ArrayAdapter<Friend?>(
            this@MainActivity,
            android.R.layout.simple_list_item_1,
            friends as List<Friend?>
        )

        friend_list.adapter = adapter

        registerFriendButton.setOnClickListener {
            name = findViewById(R.id.name)
            date = findViewById(R.id.date)

            val friend = Friend(name.text.toString(), date.text.toString())
            if (name.text.isNotEmpty() && date.text.isNotEmpty()) {
                friends.add(friend)
                adapter.notifyDataSetChanged()
            }

            name.text.clear()
            date.text.clear()

        }


        friend_list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var intent =  Intent("ntnu.katarzsz.oving3.EDIT_FRIEND")
                intent.putExtra("friend_index", position)
                intent.putExtra("name", friends[position].getName())
                intent.putExtra("date", friends[position].getDate())

                startActivityForResult(intent, RequestCode.EDIT_FRIEND)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RequestCode.EDIT_FRIEND) {
            val position =  data.getIntExtra("friend_index", -1)
            if (position >= 0) {
                 friends[position].setName(data.getStringExtra("new_name").toString())
                 friends[position].setDate(data.getStringExtra("new_date").toString())
                 adapter.notifyDataSetChanged()
            } else {
                 Log.e("ERROR", "Invalid friend index")
             }
        }
    }
}

