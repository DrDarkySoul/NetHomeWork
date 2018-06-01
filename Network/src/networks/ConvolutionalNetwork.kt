package networks

import algorithms.Convolutional
import entities.*
import readFromChanel
import writeMessageToChanel

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class ConvolutionalNetwork: Network() {
    companion object { val convolutional = Convolutional() }

    fun run(message: String) {
        val server = Server(port)
        val client = Client(host, port)
        client.writeMessageToChanel(0, message.toByteArray())
        val encoded = convolutional.encode(server.readFromChanel()[0])
        println("Encoded message: ${String(encoded)}")
        server.closeChanel()
        client.close()
        println("Received messages: ${String(convolutional.decode(encoded))}")
    }
}