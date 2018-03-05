package org.positron.examples._01_HelloWorld

import org.joml.Vector3f
import org.joml.Vector4f
import org.positron.engine.core.graphics.Color
import org.positron.engine.core.mesh.Mesh
import org.positron.engine.core.mesh.geometries.SphereGeometry
import org.positron.engine.core.mesh.material.BasicMaterial
import org.positron.engine.core.renderer.camera.PerspectiveCamera
import org.positron.engine.core.scene.light.DirectionalLight
import org.positron.engine.core.system.Demo
import org.positron.engine.core.util.MathUtils

fun main(args: Array<String>) {
    val demo = HelloWorld()
    demo.start()
}

class HelloWorld: Demo() {

    lateinit var cube: Mesh

    override fun onInit() {
        camera = PerspectiveCamera(75f, window.getWidth(), window.getHeight(), 0.1f, 1000f)
        val geometry = SphereGeometry(2f, 100, 100, 0f, MathUtils.PI2, 0f, MathUtils.PI2)
        val color = Color.BLUE
        color.a = 0.5f
        val material = BasicMaterial(color)
        cube = Mesh(geometry, material)
        scene.add(DirectionalLight(Color.GREEN))
        scene.add(cube)

        camera?.view?.lookAtLH(
            Vector3f(0f, 0f, -5f),
            Vector3f(0f, 0f, 0f),
            Vector3f(0f, 1f, 0f)
        )
    }

    override fun onStart() {

    }

    override fun onUpdate(tpf: Float) {
        cube.rotate(0.01f, 0.01f, 0f)
    }

    override fun onClose() {
        cube.destroy()
    }

}