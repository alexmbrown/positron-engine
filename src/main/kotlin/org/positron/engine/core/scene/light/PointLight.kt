package org.positron.engine.core.scene.light

import org.joml.Vector3f

class PointLight: Light {

    val position = Vector3f()
    var distance = 0f
    var decay = 1f

    constructor(): super()

    constructor(distance: Float, decay: Float): super() {
        this.distance = distance
        this.decay = decay
    }

    constructor(position: Vector3f, distance: Float, decay: Float): this(distance, decay) {
        this.position.set(position)
    }

}