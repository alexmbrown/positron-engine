package org.positron.engine.lwjgl3.input

import org.lwjgl.glfw.GLFW
import org.positron.engine.core.input.mouse.MouseInput

class GLFWMouseInput(windowRef: Long): MouseInput() {

    private var window: Long = windowRef

    init {
        GLFW.glfwSetCursorPosCallback(window, {_, xPos, yPos ->
//            println("CURSOR POS $xPos $yPos")
        })

        GLFW.glfwSetScrollCallback(window, {_, xOffset, yOffset ->
//            println("SCROLL $xOffset $yOffset")
        })

        GLFW.glfwSetMouseButtonCallback(window, {_, button, action, mods ->
//            println("MOUSE BUTTON $button $action $mods")
        })
    }

    override fun getMouseButtonState(key: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMousePosition(x: Double, y: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMousePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}