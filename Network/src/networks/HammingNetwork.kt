package networks

import algorithms.Hamming
import entities.*

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class HammingNetwork: Network() {
    companion object { val hamming = Hamming() }

    override fun run(message: String, err: Int) {
        val encodedStr = hamming.encode(message.toByteArray())
        val msgErr = if(err == -1) encodedStr else Error(encodedStr, err).makeHamming()
        val server = Server(port)
        val client = Client(host, port)

        server.getUser()
        client.writeMessage(msgErr)
        val getStr = server.readFromUser()

        server.close()
        client.close()

        hamming.decode(getStr)
    }
}