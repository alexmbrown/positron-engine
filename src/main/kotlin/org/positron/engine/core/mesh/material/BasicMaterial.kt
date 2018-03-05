package org.positron.engine.core.mesh.material

import org.positron.engine.core.graphics.Color
import org.positron.engine.core.shader.Shaders
import java.util.*

class BasicMaterial(color: Color): Material(
    Shaders.MESH_BASIC,
    Arrays.asList(
        Uniform(BasicMaterial.COLOR, color.toVec4f())
    )
) {
    companion object {
        const val COLOR   = "diffuse"
    }
}