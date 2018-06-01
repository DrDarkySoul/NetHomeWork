import entities.*

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

fun main(args: Array<String>) = Menu().run()

object Chanel {
    var msgA: ByteArray = ByteArray(0)
    var msgB: ByteArray = ByteArray(0)
    var msgC: ByteArray = ByteArray(0)

    fun getList(): List<ByteArray> = listOf(msgA, msgB, msgC)
}

fun Client.writeMessageToChanel(flag: Int, mess: ByteArray) {
    when(flag) {
        0 -> Chanel.msgA = mess
        1 -> Chanel.msgB = mess
        else -> Chanel.msgC = mess
    }
}
fun Server.readFromChanel(): List<ByteArray> = Chanel.getList()

const val byteSize = 8

operator fun Byte.get(num: Int): Boolean = this.toInt().shr(num).and(1) == 1

fun setBit(num: Int, byte: Byte, bit: Boolean): Byte {
    return if(bit) (1 shl (num) or byte.toInt()).toByte()
    else ((1 shl (num)).inv() and byte.toInt()).toByte()
}