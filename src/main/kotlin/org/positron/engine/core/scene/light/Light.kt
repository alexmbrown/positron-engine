package org.positron.engine.core.scene.light

import org.positron.engine.core.graphics.Color
import java.security.InvalidParameterException

abstract class Light {

    var color: Color
    var enabled = true

    var intensity: Float = 1f
        set(intensity) {
            if (intensity > 1f || intensity < 0f) {
                throw InvalidParameterException("Light intensity must be between 0 and 1")
            }
            this.intensity = intensity
        }

    constructor(): this(Color.WHITE)

    constructor(color: Color) {
        this.color = color
    }

    constructor(color: Color, intensity: Float) {
        this.color = color
        this.intensity = intensity
    }

}