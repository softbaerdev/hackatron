package de.fossag.hackatron

import kotlin.system.exitProcess
import kotlin.time.measureTimedValue

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
                val timedValue = measureTimedValue {
                    strategy.move(currentGameState)
                }
                messageSender.send("move|${timedValue.value}")
                Logger.log(currentGameState)
                println("Calculating move took: ${timedValue.duration}")
            }
            "game" -> {
                val widthParsed = parts[1].toInt()
                val heightParsed = parts[2].toInt()
                val playerIdParsed = parts[3].toInt()
                currentGameState = GameState(widthParsed, heightParsed, playerIdParsed)
                strategy = MostOptions()
            }
            "player" -> {
                currentGameState.addPlayer(parts[1].toInt())
            }
            "pos" -> {
                val playerId = parts[1].toInt()
                val x = parts[2].toInt()
                val y = parts[3].toInt()
                currentGameState.setPlayerHead(playerId, x, y)
                Logger.log("playerId: $playerId, x: $x, y: $y")
            }
            "die" -> {
                val playerId = parts[1].toInt()
                currentGameState.eraseDiedPlayer(playerId)
                Logger.log("playerId: $playerId")
            }
            "win" -> {
                val wins = parts[1].toInt()
                val losses = parts[2].toInt()
                Logger.log("wins: $wins, losses: $losses")
            }
            "lose" -> {
                val wins = parts[1].toInt()
                val losses = parts[2].toInt()
                println("died")
                Logger.log("wins: $wins, losses: $losses")
            }
            "error" -> {
                Logger.log("error: ${parts[1]}")
            }
            else -> {
                Logger.log("Unknown message type :(")
                exitProcess(1)
            }
        }
    }

    companion object {
        private const val CLIENT_NAME = "softbaer"
        private const val CLIENT_SECRET = "fnjfdnjfdlkmfdsk"
        private const val LOGS = true
    }
}
