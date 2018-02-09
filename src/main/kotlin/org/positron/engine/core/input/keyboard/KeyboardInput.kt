package org.positron.engine.core.input.keyboard

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback
import org.positron.engine.core.input.InputState
import org.positron.engine.core.input.InputUtils

class KeyboardInput(private val window: Long) {

    private var onEventCallback = GLFWKeyCallback.create { window, key, scancode, action, mods ->
        onEvent.onNext(convertToKeyEvent(key, action, mods))
    }

    var onEvent: Subject<KeyEvent> = PublishSubject.create()

    init {
        GLFW.glfwSetKeyCallback(window, onEventCallback)
    }

    fun getKeyState(key: Int): InputState {
        return InputUtils.actionToState(GLFW.glfwGetKey(window, key))
    }

    fun destroy() {
        onEventCallback.free()
    }

    private fun convertToKeyEvent(key: Int, glfwKeyState: Int, glfwKeyModifiers: Int): KeyEvent {
        return KeyEvent(key, InputUtils.actionToState(glfwKeyState), InputUtils.maskToModifiers(glfwKeyModifiers))
    }

}