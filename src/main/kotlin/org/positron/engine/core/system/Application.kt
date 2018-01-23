package org.positron.engine.core.system

import io.reactivex.Observable
import org.positron.engine.core.input.InputManager
import org.positron.engine.core.input.keyboard.KeyEvent
import org.positron.engine.core.input.keyboard.KeyUtils
import org.positron.engine.core.input.mouse.MouseButtonEvent
import org.positron.engine.core.input.mouse.MouseUtils


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

    /**
     * Input Events
     */
    // keyboard
    fun onKeyPress(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyPress(key))
    }

    fun onKeyRelease(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyRelease(key))
    }

    fun onKeyRepeat(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyRepeat(key))
    }

    // mouse
    fun onMouseButtonPress(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonPress(key))
    }

    fun onMouseButtonRelease(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonRelease(key))
    }

    fun onMouseButtonRepeat(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonRelease(key))
    }

}