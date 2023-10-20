package de.fossag.hackatron

import java.util.Queue

typealias Coord = Pair<Int, Int>

class GameState(val width: Int, val height: Int, val currentPlayer: Int) {
    val players: MutableList<Int> = mutableListOf()
    val playerHeads: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
    val board: Array<Array<Int?>> = Array(width) { Array(height) { null } }

    fun addPlayer(id: Int) {
        players.add(id)
    }

    fun setPlayerHead(id: Int, x: Int, y: Int) {
        playerHeads[id] = Pair(x, y)
        addPosition(id, x, y)
    }

    fun addPosition(id: Int, x: Int, y: Int) {
        board[x][y] = id;
    }

    fun canMoveTo(coord: Pair<Int, Int>): Boolean {
        val (x,y) = coord
        return board[x][y] == null
    }

    fun canMoveTo(x: Int, y: Int): Boolean {
        return board[x][y] == null
    }

    fun eraseDiedPlayer(playerId: Int) {
        board.forEachIndexed { x, ints ->
            ints.forEachIndexed { y, candidateId ->
                if (candidateId == playerId) {
                    board[x][y] = null
                }
            }
        }
    }

    fun countOptions(pos: Coord): Int {
       val visited = ArrayList<Coord>()
       val queue = ArrayDeque<Coord>()
        queue.add(pos)
        visited.add(pos)
        while (queue.isNotEmpty()) {
            val currentPos = queue.removeFirst()
            var bla = Move.entries.toTypedArray().map {  it.applyToPos(currentPos, this)}
            bla.forEach {
                if (canMoveTo(it) && !visited.contains(it)) {
                    queue.add(it)
                }
            }
            visited.add(currentPos)
        }
        return visited.count()
    }

    override fun toString(): String {
        // Transpose the matrix
        val transpose = Array(height) { IntArray(width) }
        for (i in 0..width - 1) {
            for (j in 0..height - 1) {
                if (board[i][j] == null) {
                    transpose[j][i] = -1
                } else {
                    transpose[j][i] = board[i][j]!!
                }
            }
        }
        return transpose.joinToString("\n") { it.joinToString(" ") }
                .replace("-1", "n")
    }
}