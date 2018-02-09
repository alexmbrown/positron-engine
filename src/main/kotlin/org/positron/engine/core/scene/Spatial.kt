package org.positron.engine.core.scene

import org.joml.Matrix4f

open class Spatial(parent: SceneNode?): SceneNode(parent) {

    val model: Matrix4f = Matrix4f()

}