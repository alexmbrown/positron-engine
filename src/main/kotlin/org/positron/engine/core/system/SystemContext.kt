package org.positron.engine.core.system

import org.positron.engine.core.input.InputManager
import org.positron.engine.core.system.timer.NanoTimer
import org.positron.engine.core.system.timer.Timer

abstract class SystemContext {

    var timer: Timer = NanoTimer()

    lateinit var input: InputManager
        protected set

    abstract fun close()
    var onClose: () -> Unit = {}

    abstract fun init()
    var onInit: () -> Unit = {}

    abstract fun restart()
    var onRestart: () -> Unit = {}

    abstract fun start()
    var onStart: () -> Unit = {}

    abstract fun update()
    var onUpdate: (Float) -> Unit = {}

}