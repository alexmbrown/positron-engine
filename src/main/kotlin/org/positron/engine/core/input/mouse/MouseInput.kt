package org.positron.engine.core.input.mouse

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.positron.engine.core.input.InputState

abstract class MouseInput {

    // buttons
    var onButtonEvent: Subject<MouseButtonEvent> = PublishSubject.create()
    abstract fun getMouseButtonState(key: Int): InputState

    // cursor
    var onCursorPosEvent: Subject<MouseCursorPosEvent> = PublishSubject.create()
    var onCursorEnterEvent: Subject<Boolean> = PublishSubject.create()
    abstract fun setMousePosition(x: Float, y: Float)
    abstract fun getMousePosition()

    // scroll
    var onScrollEvent: Subject<MouseScrollEvent> = PublishSubject.create()

}