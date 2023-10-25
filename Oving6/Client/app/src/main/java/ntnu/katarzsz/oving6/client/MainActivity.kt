package ntnu.katarzsz.oving6.client

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.runBlocking

class MainActivity : Activity() {
    lateinit var chat: TextView
    lateinit var chatBox: EditText
    lateinit var client: Client
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        chat = findViewById(R.id.chat)
        client = Client(chat)
        client.start()
    }

    fun onSendMessage(view: View) {
        send(client::sendToServer)
    }

    fun send(sendToServer: suspend (String) -> Unit) {
        chatBox = findViewById(R.id.chatBox)
        runBlocking {
            sendToServer(chatBox.text.toString())
        }
        chatBox.text.clear()
    }
}
