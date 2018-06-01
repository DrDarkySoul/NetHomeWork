package networks

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

open class Network {
    val port = 1234
    val host = "localhost"

    open fun run(message: String, err: Int) {}
}