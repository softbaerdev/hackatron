package de.fossag.hackatron

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
        var bla = Move.entries.toTypedArray().map { Pair(it, it.applyToPos(currentPos, gameState))}
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
        val currentPos = gameState.playerHeads[gameState.currentPlayer]!!
        var bla = Move.entries.toTypedArray().map { Pair(it, it.applyToPos(currentPos, gameState))}
        var foo = bla.mapNotNull {
            val (move, pos) = it
            if (gameState.canMoveTo(pos)) {
                val optionCount = gameState.countOptions(pos)
                Pair(move, optionCount)
            } else {
                null
            }
        }

        if (foo.isEmpty()) {
            return Move.Up.toString()
        }

        foo = foo.sortedBy { it.second }
        return foo.first.first.toString()
    }
}
