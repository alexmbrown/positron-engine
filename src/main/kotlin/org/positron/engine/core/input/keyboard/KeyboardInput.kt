package org.positron.engine.core.input.keyboard

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class KeyboardInput {

    var onEvent: Subject<KeyEvent> = PublishSubject.create()

    abstract fun getKeyState(key: Int)

}