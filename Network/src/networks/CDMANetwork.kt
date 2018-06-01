package networks

import algorithms.CDMA
import entities.*
import readFromChanel
import writeMessageToChanel

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class CDMANetwork: Network() {
    companion object { val cdma = CDMA() }

    fun run(messageList: List<ByteArray>) {
        val server = Server(port)
        val clientA = Client(host, port)
        clientA.writeMessageToChanel(0, messageList[0])
        val clientB = Client(host, port)
        clientB.writeMessageToChanel(1, messageList[1])
        val clientC = Client(host, port)
        clientC.writeMessageToChanel(2, messageList[2])
        val encoded = cdma.encode(server.readFromChanel())
        println("Encoded message: ${String(encoded)}")
        server.closeChanel()
        clientA.close()
        clientB.close()
        clientC.close()
        println("Received messages: ${cdma.decode(encoded)}")
    }
}