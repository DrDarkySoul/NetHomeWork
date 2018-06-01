package algorithms

import java.util.zip.CRC32

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class CRC {
    fun count(buf: ByteArray): Long {
        val crc = CRC32()
        crc.update(buf)
        return crc.value
    }

    fun check(str: String, crc: Long) = if(count(str.toByteArray()) == crc) println("No error!")
    else println("ERROR FOUND!")
}