package org.positron.engine.core.input.mouse

import org.positron.engine.core.input.InputModifiers
import org.positron.engine.core.input.InputState

class MouseButtonEvent(var button: Int, var state: InputState, var modifiers: InputModifiers) {

    override fun toString(): String {
        return "MouseButtonEvent(button=$button, state=$state, modifiers=$modifiers)"
    }

}