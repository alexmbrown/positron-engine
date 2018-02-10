package org.positron.engine.core.mesh.material

import org.joml.Vector4f
import org.positron.engine.core.shader.Shaders
import java.util.*

class BasicMaterial(color: Vector4f): Material(
    Shaders.BASIC,
    Arrays.asList(
        Uniform(BasicMaterial.COLOR, color)
    )
) {
    companion object {
        const val COLOR = "u_color"
    }
}