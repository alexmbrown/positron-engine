package org.positron.examples._01_HelloWorld

import org.joml.Vector3f
import org.joml.Vector4f
import org.positron.engine.core.mesh.Mesh
import org.positron.engine.core.mesh.material.BasicMaterial
import org.positron.engine.core.mesh.shapes.Sphere
import org.positron.engine.core.renderer.camera.PerspectiveCamera
import org.positron.engine.core.system.Demo

fun main(args: Array<String>) {
    val demo = HelloWorld()
    demo.start()
}

class HelloWorld: Demo() {

    lateinit var cube: Mesh

    override fun onInit() {
        camera = PerspectiveCamera(75f, window.getWidth(), window.getHeight(), 0.1f, 1000f)
        val geometry = Sphere(2.0f)
        val material = BasicMaterial(Vector4f(1f, 0f, 1f, 1f))
        cube = Mesh(geometry, material)
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
        cube.model.rotate(0.01f, 1f, 1f, 0f)
    }

    override fun onClose() {
        cube.destroy()
    }

}