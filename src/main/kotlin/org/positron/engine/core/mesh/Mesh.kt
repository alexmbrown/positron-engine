package org.positron.engine.core.mesh

import org.positron.engine.core.mesh.material.Material
import org.positron.engine.core.scene.Spatial

class Mesh(val geometry: Geometry, val material: Material): Spatial(null) {

    fun destroy() {
        geometry.destroy()
    }

}