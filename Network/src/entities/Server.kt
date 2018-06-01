package entities

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Server(port: Int) {
    private var server: ServerSocket = ServerSocket(port)
    private lateinit var client: Socket
    private lateinit var reader: BufferedReader

    fun getUser(): Socket {
        client = server.accept()
        return client
    }

    fun readFromUser(): String {
        if(client.isClosed) getUser()
        reader = BufferedReader(InputStreamReader(client.getInputStream()))
        return reader.readLine().toString()
    }

    fun close() {
        reader.close()
        client.close()
        server.close()
    }

    fun closeChanel() = server.close()
}