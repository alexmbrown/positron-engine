package org.positron.engine.core.system

import org.positron.engine.core.input.InputManager


abstract class Application {

    private var context: SystemContext = SystemUtils.createContext()
    private var started = false

    val input: InputManager
        get() = context.input

    open fun close() {}
    open fun init() {}
    open fun update(tpf: Float) {}

    init {
        context.onClose  = { close() }
        context.onUpdate = { update(it) }
        context.onStart  = { started = true }
    }

    fun restart() {
        if (!started) {
            throw IllegalStateException("restart() cannot be called before an application has been started.")
        }
        context.restart()
    }

    fun start() {
        context.init()
        init()
        context.start()
    }

    fun stop() {
        context.close()
    }

}