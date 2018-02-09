package org.positron.engine.core.input

import org.lwjgl.glfw.GLFW
import org.positron.engine.core.input.keyboard.KeyboardInput
import org.positron.engine.core.input.mouse.MouseInput

class InputManager(keyboardInput: KeyboardInput, mouseInput: MouseInput) {

    var key: KeyboardInput = keyboardInput
        private set

    var mouse: MouseInput = mouseInput
        private set

    fun destroy() {
        key.destroy()
        mouse.destroy()
    }

    fun update() {
        GLFW.glfwPollEvents()
    }

}