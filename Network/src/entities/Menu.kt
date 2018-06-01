package entities

import networks.*

/**
 * Created by Rishat_Valitov.
 * Project Network
 */

class Menu {
    private fun welcome(): Int {
        println()
        println("Welcome to NetHomeWork v.1.0.0!")
        println("Please choose number of homework: ")
        println("1. CDMA")
        println("2. CRC")
        println("3. Hamming")
        println("4. Convolutional")
        return readLine()!!.toInt()
    }

    private fun getMessageAndError(): Pair<String, String> {
        print("Write your message: ")
        val msg = readLine()!!.toString()
        println("Need error? (y/n)")
        val chr = readLine()!!.toString()
        return Pair(msg, chr)
    }

    private fun getMessage(): String {
        print("Write your message: ")
        return readLine()!!.toString()
    }

    private fun getCDMAMessage(): List<ByteArray> {
        print("Write A message: ")
        val a = readLine()!!.toByteArray()
        print("Write B message: ")
        val b = readLine()!!.toByteArray()
        print("Write C message: ")
        val c = readLine()!!.toByteArray()
        return listOf(a, b, c)
    }

    private fun getError(): Int {
        println("The bit number in which you want to makeCRC a mistake: ")
        return readLine()!!.toInt()
    }

    private fun switch() {
        when(welcome()) {
            1 -> CDMANetwork().run(getCDMAMessage())
            2 -> {
                val (msg, chr) = getMessageAndError()
                when(chr) {
                    "y" -> CRCNetwork().run(msg, getError())
                    "n" -> CRCNetwork().run(msg, -1)
                    else -> println("Wrong, try again...")
                }
            }
            3 -> {
                val (msg, chr) = getMessageAndError()
                when(chr) {
                    "y" -> HammingNetwork().run(msg, getError())
                    "n" -> HammingNetwork().run(msg, -1)
                    else -> println("Wrong, try again...")
                }
            }
            4 -> ConvolutionalNetwork().run(getMessage())
            else -> println("Wrong, try again...")
        }
    }

    fun run() { while (true) switch() }
}