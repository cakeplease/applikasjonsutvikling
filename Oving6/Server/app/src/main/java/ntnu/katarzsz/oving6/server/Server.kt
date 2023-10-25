package ntnu.katarzsz.oving6.server

import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(private var textView: TextView) {

    /**
     * Egendefinert set() som gjør at vi enkelt kan endre teksten som vises i skjermen til
     * emulatoren med
     *
     * ```
     * ui = "noe"
     * ```
     */

    val PORT = 12345

    lateinit var clientSoc: Socket


    private var ui: String? = ""
        set(str) {
            if (str != null) {
                MainScope().launch {
                    textView.text = str
                }
            }
            field = str
        }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                ui += "Starter Tjener ...\n"
                ServerSocket(PORT).use { serverSocket: ServerSocket ->

                    ui += "ServerSocket opprettet, venter på at en klient kobler seg til....\n"
                    while (true) {
                        val clientSocket = serverSocket.accept()
                        ui += "En klient koblet seg til: $clientSocket\n"

                        sendToClient(clientSocket, "Velkommen Klient!")

                        // Hent tekst fra klienten
                        readFromClient(clientSocket)
                        clientSoc = clientSocket
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                //ui = e.message
            }
        }
    }

    /*private fun readFromClient(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        ui = "Klienten sier:\n$message"
    }*/
    private suspend fun readFromClient(socket: Socket) = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val message = reader.readLine()
                Log.e("message", message)
                if (message == null) {
                    ui += "Klient koblet fra."
                    socket.close()
                    break
                } else {
                    Log.e("hmm", "whaaat")
                    //sendToClient(socket, message) // håndter andre klienter som skal motta meldingen
                    ui += "Klient: $message\n"
                }
            }
        }
    }
    /*private suspend fun handleNewClient(clientSocket: Socket) =
        coroutineScope { // this: CoroutineScope
            CoroutineScope(Dispatchers.IO).launch {
                connectedSockets.add(clientSocket)
                //ui += "En Klient koblet seg til:\n$clientSocket\n"

                //send tekst til klienten
                sendToClient(clientSocket, "Velkommen Klient!")

                // Hent tekst fra klienten
                readFromClient(clientSocket)
            }
        }*/

    suspend fun sendToClient(socket: Socket, message: String) = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            val writer = PrintWriter(socket.getOutputStream(), true)
            writer.println(message)
            ui += "Server: $message\n"
        }
    }
}
