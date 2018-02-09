package org.positron.engine.core.system

import org.lwjgl.glfw.GLFW

class Timer {

    private var last: Double = 0.0
    private var current: Double = 0.0
    private var running: Boolean = false

    fun isRunning(): Boolean {
        return running
    }

    fun start() {
        GLFW.glfwSetTime(0.0)
        running = true
    }

    fun stop() {
        if (!running) {
            throw IllegalStateException("Cannot stop timer that isn't running")
        }
        running = false
        last = 0.0
    }

    fun tick(): Float {
        last = current
        current = GLFW.glfwGetTime()
        return (current - last).toFloat()
    }

}