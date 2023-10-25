package ntnu.katarzsz.oving6.server

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.Socket
import kotlin.reflect.KSuspendFunction2

class MainActivity : Activity() {
    lateinit var server: Server
    lateinit var chat: TextView
    lateinit var chatBox: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chat = findViewById(R.id.chat)
        server = Server(chat)
        server.start()
        chatBox = findViewById(R.id.chatBox)

    }

    fun onSendMessage(view: View) {
        runBlocking {
            server.sendToClient(server.clientSoc, chatBox.text.toString())
        }
        chatBox.text.clear()
    }
    /*
        fun send(sendToClient: KSuspendFunction2<Socket, String, Job>) {
            Log.e("message", "hmmm")
            chatBox = findViewById(R.id.chatBox)
            runBlocking {
                sendToClient(chatBox.text.toString())
            }
        }*/
}
