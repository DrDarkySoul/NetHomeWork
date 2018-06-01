package entities

import kotlin.experimental.*

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Error(private val msg: String, private val err: Int) {

    fun makeCRC(): String {
        val byteArr = msg.toByteArray()
        val byteCount = (err / 8L + 1).toInt()

        val bitCount = ( err  %  8 )
        val bitMst = byteArr[byteCount]
        val bitMask: Byte = (1 shl bitCount).toByte()

        val result: Byte = if(bitMst and bitMask == 0.toByte())
            bitMst or bitMask
        else
            bitMst and bitMask.inv()
        byteArr[byteCount] = result
        return String(byteArr)
    }

    fun makeHamming(): String {
        val len = msg.length
        val hByte: Int = (err / 4)
        val bits = err % 4
        val chr = msg[hByte].toString()
        var value = chr.toInt(radix = 16)

        value = if((value shr bits) % 2 == 1 ) (value and (1 shl bits).inv())
        else (value or (1 shl bits))

        return msg.substring(0 until hByte) + value.toString(16) + msg.substring(hByte + 1 until len)
    }
}