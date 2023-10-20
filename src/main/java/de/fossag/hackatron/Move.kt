package de.fossag.hackatron

import java.util.Random
import kotlin.enums.EnumEntries

enum class Move(private val text: String) {
    Up("up"),
    Right("right"),
    Down("down"),
    Left("left");

    override fun toString(): String {
        return text
    }

    fun applyToPos(pos: Pair<Int, Int>, gameState: GameState): Pair<Int, Int> {
        when (this) {
            Up -> {
                var (x, y) = pos
                y -= 1
                if (y < 0) {
                    y = gameState.height - 1
                }
                return Pair(x, y)
            }
            Down -> {
                var (x, y) = pos
                y += 1
                if (y >= gameState.height) {
                    y = 0
                }
                return Pair(x, y)
            }
            Left -> {
                var (x, y) = pos
                x -= 1
                if (x < 0) {
                    x = gameState.width - 1
                }
                return Pair(x, y)
            }
            Right -> {
                var (x, y) = pos
                x += 1
                if (x >= gameState.width) {
                    x = 0
                }
                return Pair(x, y)
            }
        }
    }

    companion object {
        val randomEntries: List<Move>
            get() = entries.shuffled()

        val randomEntry: Move
            get() = randomEntries.first
    }
}

interface IMoveStrategy {
    fun move(gameState: GameState): String
}

class MoveUp: IMoveStrategy {
    override fun move(gameState: GameState): String {
        return Move.Up.toString()
    }
}

class DontDie: IMoveStrategy {
    override fun move(gameState: GameState): String {
        val currentPos = gameState.playerHeads[gameState.currentPlayer]!!
        var bla = Move.randomEntries.toTypedArray().map { Pair(it, it.applyToPos(currentPos, gameState))}
        bla.forEach {
            val (move, pos) = it
            if (gameState.canMoveTo(pos)) {
                return move.toString()
            }
        }
        return Move.Up.toString()
    }
}

class MoveCircle: IMoveStrategy {
    var move: Move = Move.Up
    override fun move(gameState: GameState): String {
        val moves = Move.values()
        move = moves[(moves.indexOf(move) + 1) % moves.size]
        return move.toString()
    }
}

class MostOptions: IMoveStrategy {
    override fun move(gameState: GameState): String {
        val enemiesCanMoveTo = gameState.enemiesCanMoveTo()
        val currentPos = gameState.playerHeads[gameState.currentPlayer]!!
        var possibleMoves = Move.randomEntries.toTypedArray().map { Pair(it, it.applyToPos(currentPos, gameState))}
        var movesWithOptionCount = possibleMoves.mapNotNull {
            val (move, pos) = it
            if (gameState.canMoveTo(pos)) {
                val factor = if (enemiesCanMoveTo.contains(pos)) 0.5 else 1.0
                val optionCount = gameState.countOptions(pos) * factor
                Pair(move, optionCount)
            } else {
                null
            }
        }

        if (movesWithOptionCount.isEmpty()) {
            println("Fallback")
            return Move.randomEntry.toString()
        }
        println(movesWithOptionCount)
        movesWithOptionCount = movesWithOptionCount.sortedByDescending { it.second }
        val result = movesWithOptionCount.first.first.toString()
        println(result)
        return result
    }
}
