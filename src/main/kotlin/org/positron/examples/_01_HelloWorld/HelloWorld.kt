package org.positron.examples._01_HelloWorld

import org.positron.engine.system.Demo

fun main(args: Array<String>) {
    val demo = HelloWorld()
    demo.start()
}

class HelloWorld: Demo() {

    override fun onStart() {
        println("ON START")
    }

    override fun onUpdate() {
        println("ON UPDATE")
    }

    override fun onClose() {
        println("ON CLOSE")
    }

}