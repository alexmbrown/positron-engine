package org.positron.examples._01_HelloWorld

import org.positron.engine.core.input.keyboard.Key
import org.positron.engine.core.input.keyboard.KeyEvent.Companion.onKeyDown
import org.positron.engine.core.system.Demo

fun main(args: Array<String>) {
    val demo = HelloWorld()
    demo.start()
}

class HelloWorld: Demo() {

    override fun init() {
        input.key.onEvent.filter(onKeyDown(Key.A))
            .subscribe({e -> println("TEST $e")})
    }

    override fun update(tpf: Float) {

    }

    override fun close() {
        println("ON CLOSE")
    }

}