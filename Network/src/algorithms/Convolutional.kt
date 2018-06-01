package algorithms

import byteSize
import get
import setBit

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Convolutional {
    var current: Byte = 0
    private fun next(a: Boolean): Int {
        current = when(current) {
            0.toByte() -> if(a) 0 else 1
            1.toByte() -> if(a) 1 else 3
            2.toByte() -> if(a) 2 else 0
            3.toByte() -> if(a) 3 else 2
            else -> 0
        }
        return current.toInt()
    }

    private fun previous(arr: ByteArray): Boolean {
        val a = arr[0]
        val b = arr[1]

        return when(b) {
            3.toByte() -> a == 3.toByte()
            2.toByte() -> a == 2.toByte()
            1.toByte() -> a == 1.toByte()
            0.toByte() -> a == 0.toByte()
            else -> false
        }
    }

    private fun encodeByte(bt: Byte): ByteArray {
        current = 0
        var resultLong = 0
        for(i in 0 until byteSize) {
            val nextBit = next(bt[i])
            resultLong += nextBit shl (i*2)
        }
        val result = ByteArray(2)
        result[0] = (resultLong and 0xFF).toByte()
        result[1] = (resultLong and 0xFF00).shr(8).toByte()
        return result
    }

    private fun decodeByteArr(arr: ByteArray): Byte {
        var byte: Byte = 0
        val newArr = ByteArray(2)
        newArr[1] = ((arr[1].toInt() shr 6) and 3).toByte()
        newArr[0] = ((arr[1].toInt() shr 4) and 3).toByte()
        byte = setBit(7, byte, previous(newArr))
        newArr[1] = ((arr[1].toInt() shr 4) and 3).toByte()
        newArr[0] = ((arr[1].toInt() shr 2) and 3).toByte()
        byte = setBit(6, byte, previous(newArr))
        newArr[1] = ((arr[1].toInt() shr 2) and 3).toByte()
        newArr[0] = ((arr[1].toInt() shr 0) and 3).toByte()
        byte = setBit(5, byte, previous(newArr))
        newArr[1] = ((arr[1].toInt() shr 0) and 3).toByte()
        newArr[0] = ((arr[0].toInt() shr 6) and 3).toByte()
        byte = setBit(4, byte, previous(newArr))
        newArr[1] = ((arr[0].toInt() shr 6) and 3).toByte()
        newArr[0] = ((arr[0].toInt() shr 4) and 3).toByte()
        byte = setBit(3, byte, previous(newArr))
        newArr[1] = ((arr[0].toInt() shr 4) and 3).toByte()
        newArr[0] = ((arr[0].toInt() shr 2) and 3).toByte()
        byte = setBit(2, byte, previous(newArr))
        newArr[1] = ((arr[0].toInt() shr 2) and 3).toByte()
        newArr[0] = ((arr[0].toInt() shr 0) and 3).toByte()
        byte = setBit(1, byte, previous(newArr))
        newArr[1] = ((arr[0].toInt() shr 0) and 3).toByte()
        newArr[0] = 0
        byte = setBit(0, byte, previous(newArr))
        return byte
    }

    fun encode(arr: ByteArray): ByteArray {
        val size = arr.size
        val result = ByteArray(2 * size)
        var index = 0
        arr.forEach {
            val newArr = encodeByte(it)
            result[index] = newArr[0]
            result[index+1] = newArr[1]
            index+=2
        }
        return result
    }

    fun decode(arr: ByteArray): ByteArray {
        val size = arr.size
        val result = ByteArray(size / 2)
        var count = 0
        for(i in 1..size step 2){
            val bt = decodeByteArr(arr.sliceArray(i - 1..i))
            result[count] = bt
            count += 1
        }
        return result
    }
}