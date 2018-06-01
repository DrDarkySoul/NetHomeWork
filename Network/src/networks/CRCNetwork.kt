package networks

import algorithms.CRC
import entities.*

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class CRCNetwork: Network() {
    companion object { val crc = CRC() }

    override fun run(message: String, err: Int) {
        val crcValue = crc.count(message.toByteArray())
        val msgErr = if(err == -1) message else Error(message, err).makeCRC()
        val server = Server(port)
        val client = Client(host, port)

        server.getUser()
        client.writeMessage("$crcValue.$msgErr")
        val (getCRCVal, getStrVal) = server.readFromUser().split(".", limit = 2)
        server.close()
        client.close()

        crc.check(getStrVal, getCRCVal.toLong())
    }
}