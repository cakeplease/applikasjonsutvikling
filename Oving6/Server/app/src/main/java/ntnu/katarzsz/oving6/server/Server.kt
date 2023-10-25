package ntnu.katarzsz.oving6.server

import android.widget.TextView
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
    private val port: Int = 12345
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
                ui += "Starter serveren...\n"
                ServerSocket(port).use { serverSocket: ServerSocket ->
                    ui += "ServerSocket opprettet, venter på at en klient kobler seg til....\n"
                    while (true) {
                        val clientSocket = serverSocket.accept()
                        ui += "En klient koblet seg til: $clientSocket\n"

                        sendToClient(clientSocket, "Velkommen Klient!")
                        readFromClient(clientSocket)
                        clientSoc = clientSocket
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui += e.message+"\n"
            }
        }
    }

    private suspend fun readFromClient(socket: Socket) = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val message = reader.readLine()
                if (message == null) {
                    ui += "Klient koblet fra."
                    socket.close()
                    break
                } else {
                    ui += "Klient: $message\n"
                }
            }
        }
    }

    suspend fun sendToClient(socket: Socket, message: String) = coroutineScope {
        CoroutineScope(Dispatchers.IO).launch {
            val writer = PrintWriter(socket.getOutputStream(), true)
            writer.println(message)
            ui += "Server: $message\n"
        }
    }
}
