package org.positron.engine.core.system.timer

interface Timer {
    fun isRunning(): Boolean
    fun start()
    fun stop()
    fun tick(): Float
}