package entities

import java.io.PrintWriter
import java.net.Socket

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Client(private val host: String, private val port: Int) {
    private var client = Socket(host, port)
    private lateinit var out: PrintWriter

    fun writeMessage(mess: String) {
        if(client.isClosed)
            client = Socket(host, port)
        out = PrintWriter(client.getOutputStream())
        out.write(mess)
        out.flush()
        out.close()
    }

    fun close() = client.close()
}