package ntnu.katarzsz.oving3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditFriendActivity : Activity() {

    lateinit var editFriendButton: Button
    lateinit var name: EditText
    lateinit var date: EditText
    lateinit var friend_name: String
    lateinit var friend_date: String
    var friend_index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_friend)
        friend_name = intent.getStringExtra("name") as String
        friend_date = intent.getStringExtra("date") as String
        friend_index = intent.getIntExtra("friend_index", -1)
        name = findViewById(R.id.edit_name)
        date = findViewById(R.id.edit_date)
        name.setText(friend_name)
        date.setText(friend_date)

        editFriendButton = findViewById(R.id.editFriendButton)

        editFriendButton.setOnClickListener {
            name = findViewById(R.id.edit_name)
            date = findViewById(R.id.edit_date)

            val intent = Intent()
            intent.putExtra("new_name", name.text.toString())
            intent.putExtra("new_date", date.text.toString())
            intent.putExtra("friend_index", friend_index)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}