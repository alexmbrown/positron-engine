package org.positron.engine.lwjgl3.input

import org.lwjgl.glfw.GLFW
import org.positron.engine.core.input.InputModifiers
import org.positron.engine.core.input.InputState

class GLFWInputUtils {
    companion object {

        fun maskToModifiers(glfwModifiers: Int): InputModifiers {
            return InputModifiers(
                (glfwModifiers and GLFW.GLFW_MOD_ALT) == GLFW.GLFW_MOD_ALT,
                (glfwModifiers and GLFW.GLFW_MOD_CONTROL) == GLFW.GLFW_MOD_CONTROL,
                (glfwModifiers and GLFW.GLFW_MOD_SHIFT) == GLFW.GLFW_MOD_SHIFT,
                (glfwModifiers and GLFW.GLFW_MOD_SUPER) == GLFW.GLFW_MOD_SUPER
            )
        }

        fun actionToState(glfwAction: Int): InputState {
            return when (glfwAction) {
                GLFW.GLFW_PRESS -> InputState.PRESS
                GLFW.GLFW_RELEASE -> InputState.RELEASE
                else -> InputState.REPEAT
            }
        }

    }
}