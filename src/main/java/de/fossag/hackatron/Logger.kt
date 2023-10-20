package de.fossag.hackatron

object Logger {

    private const val ENABLED: Boolean = false

    fun log(msg: Any?) {
        if (!ENABLED) return
        println(msg)
    }
}
