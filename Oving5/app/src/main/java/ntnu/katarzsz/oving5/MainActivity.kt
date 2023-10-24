package ntnu.katarzsz.oving5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText


class MainActivity : Activity() {
    private lateinit var name: EditText
    private lateinit var bankAccount: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onRegister(view: View) {
        val intent = Intent("ntnu.katarzsz.oving5.PLAY_GAME")
        name = findViewById(R.id.name)
        bankAccount = findViewById(R.id.bank_account)
        intent.putExtra("name", name.text.toString())
        intent.putExtra("bankAccount", bankAccount.text.toString())
        startActivityForResult(intent, 1)
    }

}
