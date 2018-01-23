package org.positron.engine.core.input.keyboard

import org.positron.engine.core.input.InputState

class KeyUtils {

    companion object {
        fun onKeyPress(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.state === InputState.PRESS }
        }

        fun onKeyRelease(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.state === InputState.RELEASE }
        }

        fun onKeyRepeat(key: Int): (KeyEvent) -> Boolean  {
            return { event: KeyEvent -> event.key == key && event.state === InputState.REPEAT }
        }
    }

}