package org.positron.engine.core.input.keyboard

class KeyModifiers(var alt: Boolean, var ctrl: Boolean, var shift: Boolean, var system: Boolean) {

    override fun toString(): String {
        return "KeyModifiers(alt=$alt, ctrl=$ctrl, shift=$shift, system=$system)"
    }

}