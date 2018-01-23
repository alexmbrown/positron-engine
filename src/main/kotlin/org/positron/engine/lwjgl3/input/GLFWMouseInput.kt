package org.positron.engine.lwjgl3.input

import org.lwjgl.glfw.GLFW
import org.positron.engine.core.input.InputState
import org.positron.engine.core.input.mouse.MouseButtonEvent
import org.positron.engine.core.input.mouse.MouseCursorPosEvent
import org.positron.engine.core.input.mouse.MouseInput
import org.positron.engine.core.input.mouse.MouseScrollEvent
import org.positron.engine.lwjgl3.input.GLFWInputUtils.Companion.actionToState

class GLFWMouseInput(windowRef: Long): MouseInput() {

    private var window: Long = windowRef
    private var prevXPos: Double = 0.0
    private var prevYPos: Double = 0.0

    init {
        GLFW.glfwSetCursorPosCallback(window, {_, xPos, yPos ->
            val dx = xPos - prevXPos
            val dy = yPos - prevYPos
            prevXPos = xPos
            prevYPos = yPos
            onCursorPosEvent.onNext(MouseCursorPosEvent(dx.toFloat(), dy.toFloat()))
        })

        GLFW.glfwSetCursorEnterCallback(window, {_, entered ->
            onCursorEnterEvent.onNext(entered)
        })

        GLFW.glfwSetScrollCallback(window, {_, xOffset, yOffset ->
            onScrollEvent.onNext(MouseScrollEvent(xOffset.toFloat(), yOffset.toFloat()))
        })

        GLFW.glfwSetMouseButtonCallback(window, {_, button, action, mods ->
            onButtonEvent.onNext(convertToButtonEvent(button, action, mods))
        })
    }

    override fun getMouseButtonState(key: Int): InputState {
        return actionToState(GLFW.glfwGetMouseButton(window, key))
    }

    override fun setMousePosition(x: Float, y: Float) {
        GLFW.glfwSetCursorPos(window, x.toDouble(), y.toDouble())
    }

    override fun getMousePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun convertToButtonEvent(button: Int, glfwKeyState: Int, glfwKeyModifiers: Int): MouseButtonEvent {
        return MouseButtonEvent(button, GLFWInputUtils.actionToState(glfwKeyState), GLFWInputUtils.maskToModifiers(glfwKeyModifiers))
    }

}