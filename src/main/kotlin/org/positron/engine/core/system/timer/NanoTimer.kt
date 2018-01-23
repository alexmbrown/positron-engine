package org.positron.engine.core.system.timer

class NanoTimer: Timer {

    private var last: Long = -1
    private var current: Long = -1
    private var running: Boolean = false

    override fun isRunning(): Boolean {
        return running
    }

    override fun start() {
        current = System.nanoTime()
        running = true
    }

    override fun stop() {
        if (!running) {
            throw IllegalStateException("Cannot stop timer that isn't running")
        }
        running = false
        last = -1
    }

    override fun tick(): Float {
        last = current
        current = System.nanoTime()
        return (current - last) / 1000000000.0f
    }

}