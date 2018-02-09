package org.positron.engine.core.renderer.camera

import org.positron.engine.core.util.BGFXUtils.Companion.zZeroToOne

class PerspectiveCamera(var fov: Float, var width: Int, var height: Int, var near: Float, var far: Float): Camera() {

    init {
        update()
    }

    override fun update() {
        val fovRadians = fov * Math.PI.toFloat() / 180.0f
        val aspect = width.toFloat() / height.toFloat()
        projection.setPerspectiveLH(fovRadians, aspect, near, far, zZeroToOne)

    }
}