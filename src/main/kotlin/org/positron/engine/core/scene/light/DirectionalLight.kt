package org.positron.engine.core.scene.light

import org.joml.Vector3f
import org.positron.engine.core.graphics.Color

class DirectionalLight: Light {

    var direction: Vector3f = Vector3f(0f, -1f, 0f)
    constructor(): super()

    constructor(color: Color): super(color)

    constructor(direction: Vector3f): this() {
        this.direction = direction
    }

    constructor(direction: Vector3f, color: Color): this(direction) {
        this.color = color
    }

}