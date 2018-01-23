package org.positron.engine.lwjgl3.input

import org.lwjgl.glfw.GLFW
import org.positron.engine.core.input.keyboard.KeyEvent
import org.positron.engine.core.input.InputState
import org.positron.engine.core.input.keyboard.KeyboardInput
import org.positron.engine.lwjgl3.input.GLFWInputUtils.Companion.actionToState
import org.positron.engine.lwjgl3.input.GLFWInputUtils.Companion.maskToModifiers

class GLFWKeyboardInput(windowRef: Long): KeyboardInput() {

    private var window: Long = windowRef

    init {
        GLFW.glfwSetKeyCallback(window, {_, key, _, action, mods ->
            onEvent.onNext(convertToKeyEvent(key, action, mods))
        })
    }

    override fun getKeyState(key: Int): InputState {
        return actionToState(GLFW.glfwGetKey(window, key))
    }

    private fun convertToKeyEvent(key: Int, glfwKeyState: Int, glfwKeyModifiers: Int): KeyEvent {
        return KeyEvent(key, actionToState(glfwKeyState), maskToModifiers(glfwKeyModifiers))
    }

}