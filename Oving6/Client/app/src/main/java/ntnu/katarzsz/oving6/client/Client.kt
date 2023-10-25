package ntnu.katarzsz.oving6.client

import android.widget.TextView
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(private val textView: TextView) {
    private val serverIP: String = "10.0.2.2"
    private val port: Int = 12345

    private var server: Socket? = null

    private var ui: String? = ""
    set(str) {
        MainScope().launch {
            textView.text = str
        }
        field = str
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ui += "Kobler til serveren...\n"
                server = Socket(serverIP, port)
                ui += "Koblet til server: $server\n"
                readFromServer()
                sendToServer("Heisann Tjener! Hyggelig å hilse på deg.")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun readFromServer() = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            server?.let {
                while (true) {
                    val reader = BufferedReader(InputStreamReader(it.getInputStream()))
                    val message = reader.readLine()
                    if (message !== null) {
                        ui += "Server: $message\n"
                    } else {
                        it.close()
                        break
                    }
                }
            }
        }
    }

    suspend fun sendToServer(message: String) = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            server?.let {
                val writer = PrintWriter(it.getOutputStream(), true)
                writer.println(message)
                ui += "Klient: $message\n"
            }
        }
    }
}
