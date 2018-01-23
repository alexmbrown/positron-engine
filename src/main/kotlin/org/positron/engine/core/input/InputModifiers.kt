package org.positron.engine.core.input

class InputModifiers(var alt: Boolean, var ctrl: Boolean, var shift: Boolean, var system: Boolean) {

    override fun toString(): String {
        return "InputModifiers(alt=$alt, ctrl=$ctrl, shift=$shift, system=$system)"
    }

}