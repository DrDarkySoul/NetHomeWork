package algorithms

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Hamming {
    private fun getRaw(num: Byte): Int {
        val top = (num.toInt() and 0b11110000) shl 4
        val mid = (num.toInt() and 0b1110) shl 3
        val bot = (num.toInt() and 0b1) shl 2
        return top or mid or bot
    }

    private fun makeByte(num: Int): Byte {
        val top = (num and 0b1111_0000_0000) shr 4
        val mid = (num and 0b111_0000) shr 3
        val bot = (num and 0b100) shr 2
        return (top or mid or bot).toByte()
    }

     private fun makeByteArr(arr: IntArray): ByteArray {
        val size = arr.size
        val result = ByteArray(size)
        var i = 0
        arr.forEach {
            result[i] = makeByte(it)
            i += 1
        }
        return result
    }

    private fun getControl(num: Int): List<Int>{
        val f = (num % 2) xor ((num shr 1) % 2) xor ((num shr 3) % 2) xor ((num shr 4) % 2) xor ((num shr 6) % 2)
        val s = (num % 2) xor ((num shr 2) % 2) xor ((num shr 3) % 2) xor ((num shr 5) % 2) xor ((num shr 6) % 2)
        val t = ((num shr 1) % 2) xor ((num shr 2) % 2) xor ((num shr 3) % 2) xor ((num shr 7) % 2)
        val k = ((num shr 4) % 2) xor ((num shr 5) % 2) xor ((num shr 6) % 2) xor ((num shr 7) % 2)
        return listOf(f, s, t, k)
    }

    private fun makeByteEncode(num: Byte): Int {
        val raw = getRaw(num)
        val ctrl = getControl(num.toInt())
        var result = raw

        if(ctrl[0] == 1) result = result or 0b1
        if(ctrl[1] == 1) result = result or 0b10
        if(ctrl[2] == 1) result = result or 0b1000
        if(ctrl[3] == 1) result = result or 0b1000_0000

        return result
    }

    private fun getIntArray(str: String): IntArray {
        val len = str.length
        val result = IntArray(len / 3)
        for((i, j) in (2..len step 3).withIndex()) result[i] = str.substring(j-2..j).toInt(radix = 16)
        return result
    }

    private fun fixError(index: Int, num: Int): Int{
        val f = ((num shr 2) % 2) xor ((num shr 4) % 2) xor ((num shr 6) % 2) xor ((num shr 8) % 2) xor ((num shr 10) % 2)
        val s = ((num shr 2) % 2) xor ((num shr 5) % 2) xor ((num shr 6) % 2) xor ((num shr 9) % 2) xor ((num shr 10) % 2)
        val t = ((num shr 4) % 2) xor ((num shr 5) % 2) xor ((num shr 6) % 2) xor ((num shr 11) % 2)
        val k = ((num shr 8) % 2) xor ((num shr 9) % 2) xor ((num shr 10) % 2) xor ((num shr 11) % 2)

        var errPos = 0

        if(f != num % 2)         errPos += 1
        if(s != (num shr 1) % 2) errPos += 2
        if(t != (num shr 3) % 2) errPos += 4
        if(k != (num shr 7) % 2) errPos += 8

        if(errPos != 0) println("Error was found in $index byte, in $errPos position and fixed!")

        return if((num shr (errPos - 1)) % 2 == 1) (num and ((0b1 shl (errPos - 1)).inv()))
        else (num or (0b1 shl (errPos - 1)))
    }

    fun encode(array: ByteArray): String {
        var result = ""
        array.forEach { result += makeByteEncode(it).toString(16) }
        return result
    }

    fun decode(str: String) {
        val arr = getIntArray(str)
        println("Received message: ${String(Hamming().makeByteArr(arr))} ")
        val size = arr.size
        for(i in 0 until size)
            arr[i] = fixError(i, arr[i])
        println("Fixed message: ${String(Hamming().makeByteArr(arr))} ")
    }
}