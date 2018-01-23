package org.positron.engine.core.input.keyboard

class KeyEvent(var key: Int, var action: KeyAction, var modifiers: KeyModifiers) {

    companion object {
        fun onKeyDown(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyAction.PRESS }
        }

        fun onKeyUp(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyAction.RELEASE }
        }

        fun onKeyRepeat(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.action === KeyAction.REPEAT }
        }
    }

    override fun toString(): String {
        return "KeyEvent(key=$key, action=$action, modifiers=$modifiers)"
    }
}