package org.positron.engine.core.scene

import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

open class Spatial(parent: SceneNode?): SceneNode(parent) {

    private val model = Matrix4f()


    private val rotation = Quaternionf()
    private val translation = Vector3f()
    private val scale = Vector3f(1f)

    fun rotate(angle: Float) {
        rotation.rotate(angle, angle, angle)
    }

    fun rotate(angleX: Float, angleY: Float, angleZ: Float) {
        rotation.rotate(angleX, angleY, angleZ)
    }

    fun scale(scale: Float) {
        this.scale.x = scale
        this.scale.y = scale
        this.scale.z = scale
    }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float) {
        scale.x = scaleX
        scale.y = scaleY
        scale.z = scaleZ
    }

    fun translate(x: Float, y: Float, z: Float) {
        translation.x = x
        translation.y = y
        translation.z = z
    }

    fun getModel(): Matrix4f {
        return Matrix4f()
            .scaleLocal(scale.x, scale.y, scale.z)
            .rotateLocal(rotation)
            .translateLocal(translation)
    }

}