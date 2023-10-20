package de.fossag.hackatron

import kotlin.system.exitProcess

class HackatronClient : IHackatronClient {

    private lateinit var messageSender: IMessageSender
    private lateinit var currentGameState: GameState
    private lateinit var strategy: IMoveStrategy

    override fun setMessageSender(messageSender: IMessageSender) {
        this.messageSender = messageSender
    }

    override fun onMessage(message: String) {
        val parts = message
                .split("|")
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val messageType = parts[0]
        when (messageType) {
            "motd" -> {
                messageSender.send("join|$CLIENT_NAME|$CLIENT_SECRET")
            }
            "tick" -> {
                messageSender.send("move|${strategy.move(currentGameState)}")
                println(currentGameState)
            }
            "game" -> {
                val widthParsed = parts[1].toInt()
                val heightParsed = parts[2].toInt()
                val playerIdParsed = parts[3].toInt()
                currentGameState = GameState(widthParsed, heightParsed, playerIdParsed)
                strategy = DontDie()
            }
            "player" -> {
                currentGameState.addPlayer(parts[1].toInt())
            }
            "pos" -> {
                val playerId = parts[1].toInt()
                val x = parts[2].toInt()
                val y = parts[3].toInt()
                currentGameState.setPlayerHead(playerId, x, y)
                println("playerId: $playerId, x: $x, y: $y")
            }
            "die" -> {
                val playerId = parts[1].toInt()
                currentGameState.eraseDiedPlayer(playerId)
                println("playerId: $playerId")
            }
            "win" -> {
                val wins = parts[1].toInt()
                val losses = parts[2].toInt()
                println("wins: $wins, losses: $losses")
            }
            "lose" -> {
                val wins = parts[1].toInt()
                val losses = parts[2].toInt()
                println("wins: $wins, losses: $losses")
            }
            "error" -> {
                println("error: ${parts[1]}")
            }
            else -> {
                println("Unknown message type :(")
                exitProcess(1)
            }
        }
    }

    companion object {
        private const val CLIENT_NAME = "softbaer"
        private const val CLIENT_SECRET = "fnjfdnjfdlkmfdsk"
    }
}
