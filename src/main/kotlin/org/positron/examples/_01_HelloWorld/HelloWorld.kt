package org.positron.examples._01_HelloWorld

import org.positron.engine.core.system.Demo

fun main(args: Array<String>) {
    val demo = HelloWorld()
    demo.start()
}

class HelloWorld: Demo() {

    override fun init() {
        input.mouse.onButtonEvent.subscribe({e -> println("MOUSE $e")})
    }

    override fun update(tpf: Float) {

    }

    override fun close() {
        println("ON CLOSE")
    }

}