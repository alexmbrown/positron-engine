package org.positron.engine.core.input

import org.positron.engine.core.input.keyboard.KeyboardInput
import org.positron.engine.core.input.mouse.MouseInput

class InputManager(keyboard: KeyboardInput, mouse: MouseInput) {

    var key: KeyboardInput = keyboard
        private set

    var mouse: MouseInput = mouse
        private set

}