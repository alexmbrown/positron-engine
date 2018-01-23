package org.positron.engine.core.input.mouse

import org.positron.engine.core.input.InputState
import org.positron.engine.core.input.mouse.MouseButtonEvent

class MouseUtils {

    companion object {
        fun onMouseButtonPress(button: Int): (MouseButtonEvent) -> Boolean  {
            return { event: MouseButtonEvent -> event.button == button && event.state === InputState.PRESS }
        }

        fun onMouseButtonRelease(button: Int): (MouseButtonEvent) -> Boolean  {
            return { event: MouseButtonEvent -> event.button == button && event.state === InputState.RELEASE }
        }

        fun onMouseButtonRepeat(button: Int): (MouseButtonEvent) -> Boolean  {
            return { event: MouseButtonEvent -> event.button == button && event.state === InputState.REPEAT }
        }
    }

}