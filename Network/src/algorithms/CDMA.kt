package algorithms

import byteSize
import get
import setBit

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class CDMA {
    private val a =    byteArrayOf(-1, 1, -1, 1, -1, 1, -1, 1)
    private val notA = byteArrayOf(1, -1, 1, -1, 1, -1, 1, -1)
    private val b =    byteArrayOf(1, -1, 1, -1, -1, 1, -1, 1)
    private val notB = byteArrayOf(-1, 1, -1, 1, 1, -1, 1, -1)
    private val c =    byteArrayOf(1, -1, -1, 1, 1, -1, -1, 1)
    private val notC = byteArrayOf(-1, 1, 1, -1, -1, 1, 1, -1)

    fun encode(array: List<ByteArray>): ByteArray {
        var size = 0
        array.forEach { if (it.size > size) size = it.size }
        val result = ByteArray(size * byteSize * byteSize)

        for(i in 0..2) {
            var offset = 0
            array[i].forEach {
                for(j in 0 until byteSize){
                    val op = when(i){
                        0    -> if(it[j]) a else notA
                        1    -> if(it[j]) b else notB
                        else -> if(it[j]) c else notC
                    }
                    for (k in 0 until byteSize)
                        result[offset * byteSize + k] = (result[offset * byteSize + k] + op[k]).toByte()
                    offset += 1
                }
            }
        }
        return result
    }

    private fun getBit(bt: ByteArray, op: ByteArray): Boolean {
        var result = 0
        for(i in 0 until byteSize) {
           result += bt[i] * op[i]
        }
        if(result == 8) return true
        return if(result == -8) false
        else {
            println("Decode error!")
            false
        }
    }

    private fun getByte(arr: ByteArray, op: ByteArray): Byte {
        var bt: Byte = 0
        for(i in 0 until arr.size step 8)
            bt = setBit(i / 8, bt, getBit(arr.sliceArray(i until i + byteSize), op))
        return bt
    }

    fun decode(arr: ByteArray): List<String> {
        val size = arr.size
        val aArr = ByteArray((size / (byteSize * byteSize)))
        val bArr = ByteArray((size / (byteSize * byteSize)))
        val cArr = ByteArray((size / (byteSize * byteSize)))

        for(i in 0 until size step byteSize * byteSize){
            aArr[i/(byteSize * byteSize)] = getByte(arr.sliceArray(i until i + byteSize * byteSize), a)
            bArr[i/(byteSize * byteSize)] = getByte(arr.sliceArray(i until i + byteSize * byteSize), b)
            cArr[i/(byteSize * byteSize)] = getByte(arr.sliceArray(i until i + byteSize * byteSize), c)
        }
        return listOf(String(aArr), String(bArr), String(cArr))
    }
}