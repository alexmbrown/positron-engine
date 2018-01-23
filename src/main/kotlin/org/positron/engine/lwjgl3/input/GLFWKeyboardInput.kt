package org.positron.engine.lwjgl3.input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_MOD_ALT
import org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER
import org.positron.engine.core.input.keyboard.KeyState
import org.positron.engine.core.input.keyboard.KeyEvent
import org.positron.engine.core.input.keyboard.KeyModifiers
import org.positron.engine.core.input.keyboard.KeyboardInput

class GLFWKeyboardInput(windowRef: Long): KeyboardInput() {

    private var window: Long = windowRef

    init {
        GLFW.glfwSetKeyCallback(window, {_, key, _, action, mods ->
            onEvent.onNext(convertToKeyEvent(key, action, mods))
        })
    }

    override fun getKeyState(key: Int): KeyState {
        return convertToKeyState(GLFW.glfwGetKey(window, key))
    }

    private fun convertToKeyEvent(key: Int, glfwKeyState: Int, glfwKeyModifiers: Int): KeyEvent {
        return KeyEvent(key, convertToKeyState(glfwKeyState), convertToKeyModifiers(glfwKeyModifiers))
    }

    private fun convertToKeyState(glfwKeyState: Int): KeyState {
        return when (glfwKeyState) {
            GLFW_PRESS -> KeyState.PRESS
            GLFW_RELEASE -> KeyState.RELEASE
            else -> KeyState.REPEAT
        }
    }

    private fun convertToKeyModifiers(glfwModifiers: Int): KeyModifiers {
        return KeyModifiers(
            (glfwModifiers and GLFW_MOD_ALT) == GLFW_MOD_ALT,
            (glfwModifiers and GLFW_MOD_CONTROL) == GLFW_MOD_CONTROL,
            (glfwModifiers and GLFW_MOD_SHIFT) == GLFW_MOD_SHIFT,
            (glfwModifiers and GLFW_MOD_SUPER) == GLFW_MOD_SUPER
        )
    }

}