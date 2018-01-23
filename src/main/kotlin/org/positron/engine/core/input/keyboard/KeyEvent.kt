package org.positron.engine.core.input.keyboard

import org.positron.engine.core.input.InputModifiers
import org.positron.engine.core.input.InputState


class KeyEvent(var key: Int, var state: InputState, var modifiers: InputModifiers) {

    override fun toString(): String {
        return "KeyEvent(key=$key, state=$state, modifiers=$modifiers)"
    }

}