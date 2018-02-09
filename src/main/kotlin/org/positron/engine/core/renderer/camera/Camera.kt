package org.positron.engine.core.renderer.camera

import org.joml.Matrix4f
import org.positron.engine.core.scene.Spatial

abstract class Camera: Spatial(null) {

    val projection = Matrix4f()
    val view = Matrix4f()

    abstract fun update()

}