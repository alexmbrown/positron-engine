package org.positron.engine.core.input.keyboard

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.positron.engine.core.input.InputState

abstract class KeyboardInput {

    var onEvent: Subject<KeyEvent> = PublishSubject.create()

    abstract fun getKeyState(key: Int): InputState

}