package de.fossag.hackatron

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

/**
 * Simple wrapper class for [IHackatronClient] implementations that handles
 * network and logging.
 */
class HackatronWrapper(private val host: String, private val port: Int, private val hackatronClient: IHackatronClient) {
    @Throws(IOException::class)
    fun run() {
        println("Connecting to $host:$port")

        // Set up socket and wire up streams
        val socket = Socket(host, port)
        val out = socket.getOutputStream()
        val writer = PrintWriter(out, true)
        val `in` = socket.getInputStream()
        val reader = BufferedReader(InputStreamReader(`in`))
        println("Connection successful")

        // The message sender logs the outgoing message and stuffs it into the output stream
        hackatronClient.setMessageSender { s: String ->
            Logger.log("OUT MSG: $s")
            writer.println(s)
        }
        while (true) {
            if (socket.isClosed) {
                return
            }
            val line = reader.readLine() ?: return
            Logger.log("IN MSG: $line")
            hackatronClient.onMessage(line)
        }
    }
}
