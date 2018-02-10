package org.positron.engine.core.mesh.material

import org.joml.Vector4f
import org.positron.engine.core.shader.Shaders
import java.util.*

class WireframeMaterial(color: Vector4f): Material(
    Shaders.WIREFRAME,
    Arrays.asList(
        Uniform(WireframeMaterial.COLOR, color)
    )
) {
    companion object {
        const val COLOR = "a_color"
    }
}