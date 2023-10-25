package ntnu.katarzsz.oving6.client

import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(private val textView: TextView) {
    private val SERVER_IP: String = "10.0.2.2"
    private val SERVER_PORT: Int = 12345

    var server: Socket? = null
        private set

    /**
     * Egendefinert set() som gjør at vi enkelt kan endre teksten som vises i skjermen til
     * emulatoren med
     *
     * ```
     * ui = "noe"
     * ```
     */
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
                ui += "Kobler til tjener...\n"

                server = Socket(SERVER_IP, SERVER_PORT)

                ui += "Koblet til tjener: $server\n"

                startReadFromServer()

                sendToServer("Heisann Tjener! Hyggelig å hilse på deg.")
            } catch (e: IOException) {
                e.printStackTrace()
                //e.message?.let { incomingMessages.add(it) }
            }
        }
    }
  /*  fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            ui = "Kobler til tjener..."
            try {
                Socket(SERVER_IP, SERVER_PORT).use { socket: Socket ->
                    ui = "Koblet til tjener:\n$socket"

                    delay(5000)

                    readFromServer(socket)

                    delay(5000)

                    sendToServer("Heisann Tjener! Hyggelig å hilse på deg")

                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }*/
  private suspend fun startReadFromServer() = coroutineScope {
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
    private fun readFromServer(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        ui += "Server: $message\n"
    }

    suspend fun sendToServer(message: String) = coroutineScope {
        Log.i("Send", message)
        CoroutineScope(Dispatchers.IO).launch {
            server?.let {
                val writer = PrintWriter(it.getOutputStream(), true)
                writer.println(message)
                ui += "Klient: $message\n"
                //outgoingMessages.add(message)
            }
        }
    }
    /*private fun sendToServer(socket: Socket, message: String) {
        val writer = PrintWriter(socket.getOutputStream(), true)
        writer.println(message)
        ui = "You: $message"
    }*/
}
