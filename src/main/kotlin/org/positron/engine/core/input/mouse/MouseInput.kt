package org.positron.engine.core.input.mouse

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWCursorEnterCallback
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWMouseButtonCallback
import org.lwjgl.glfw.GLFWScrollCallback
import org.positron.engine.core.input.InputState
import org.positron.engine.core.input.InputUtils

class MouseInput(private val window: Long){

    // buttons
    private var onButtonCallback = GLFWMouseButtonCallback.create { _, button, action, mods ->
        onButtonEvent.onNext(convertToButtonEvent(button, action, mods))
    }
    var onButtonEvent: Subject<MouseButtonEvent> = PublishSubject.create()

    // cursor
    private var onCursorPosCallback = GLFWCursorPosCallback.create { _, xPos, yPos ->
        val dx = xPos - prevXPos
        val dy = yPos - prevYPos
        prevXPos = xPos
        prevYPos = yPos
        onCursorPosEvent.onNext(MouseCursorPosEvent(dx.toFloat(), dy.toFloat()))
    }
    var onCursorPosEvent: Subject<MouseCursorPosEvent> = PublishSubject.create()
    private var onCursorEnterCallback = GLFWCursorEnterCallback.create { _, entered ->
        onCursorEnterEvent.onNext(entered)
    }
    var onCursorEnterEvent: Subject<Boolean> = PublishSubject.create()

    // scroll
    private var onScrollCallback = GLFWScrollCallback.create { _, xOffset, yOffset ->
        onScrollEvent.onNext(MouseScrollEvent(xOffset.toFloat(), yOffset.toFloat()))
    }
    var onScrollEvent: Subject<MouseScrollEvent> = PublishSubject.create()

    private var prevXPos: Double = 0.0
    private var prevYPos: Double = 0.0

    init {
        GLFW.glfwSetCursorPosCallback(window, onCursorPosCallback)
        GLFW.glfwSetCursorEnterCallback(window, onCursorEnterCallback)
        GLFW.glfwSetScrollCallback(window, onScrollCallback)
        GLFW.glfwSetMouseButtonCallback(window, onButtonCallback)
    }

    fun destroy() {
        onCursorEnterCallback.free()
        onScrollCallback.free()
        onButtonCallback.free()
        onCursorPosCallback.free()
    }

    fun getMouseButtonState(key: Int): InputState {
        return InputUtils.actionToState(GLFW.glfwGetMouseButton(window, key))
    }

    fun setMousePosition(x: Float, y: Float) {
        GLFW.glfwSetCursorPos(window, x.toDouble(), y.toDouble())
    }

    fun getMousePosition(): Vector2f {
        val xPos = DoubleArray(1)
        val yPos = DoubleArray(1)
        GLFW.glfwGetCursorPos(window, xPos, yPos)
        return Vector2f(xPos[0].toFloat(), yPos[0].toFloat())
    }

    private fun convertToButtonEvent(button: Int, glfwKeyState: Int, glfwKeyModifiers: Int): MouseButtonEvent {
        return MouseButtonEvent(button, InputUtils.actionToState(glfwKeyState), InputUtils.maskToModifiers(glfwKeyModifiers))
    }

}