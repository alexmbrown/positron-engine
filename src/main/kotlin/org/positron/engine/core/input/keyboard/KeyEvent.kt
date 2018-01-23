package org.positron.engine.core.input.keyboard

class KeyEvent(var key: Int, var action: KeyState, var modifiers: KeyModifiers) {

    companion object {
        fun onKeyDown(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyState.PRESS }
        }

        fun onKeyUp(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyState.RELEASE }
        }

        fun onKeyRepeat(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyState.REPEAT }
        }
    }

    override fun toString(): String {
        return "KeyEvent(key=$key, action=$action, modifiers=$modifiers)"
    }
}