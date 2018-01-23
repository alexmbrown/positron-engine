package org.positron.engine.core.input.mouse

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class MouseInput {

    // buttons
    var onButtonEvent: Subject<MouseButtonEvent> = PublishSubject.create()
    abstract fun getMouseButtonState(key: Int)

    // cursor
    var onCursorEvent: Subject<MouseCursorEvent> = PublishSubject.create()
    abstract fun setMousePosition(x: Double, y: Double)
    abstract fun getMousePosition()

    // scroll
    var onScrollEvent: Subject<MouseScrollEvent> = PublishSubject.create()

}