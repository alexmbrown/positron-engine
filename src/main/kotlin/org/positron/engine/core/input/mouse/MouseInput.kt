package org.positron.engine.core.input.mouse

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class MouseInput {

    // buttons
    var onMouseButtonDown: Observable<MouseButtonEvent> = PublishSubject.create()
    var onMouseButtonUp: Observable<MouseButtonEvent> = PublishSubject.create()
    abstract fun getMouseButtonState(key: Int)

    // cursor
    var onMouseEnter: Observable<MouseCursorEvent> = PublishSubject.create()
    var onMouseMove: Observable<MouseCursorEvent> = PublishSubject.create()
    abstract fun setMousePosition(x: Double, y: Double)
    abstract fun getMousePosition()

    // scroll
    var onScroll: Observable<MouseScrollEvent> = PublishSubject.create()

}