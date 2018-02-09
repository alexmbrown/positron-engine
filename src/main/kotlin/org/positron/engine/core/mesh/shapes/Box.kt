package org.positron.engine.core.mesh.shapes

import org.positron.engine.core.mesh.Geometry
import org.positron.engine.core.scene.IndexBuffer
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.scene.VertexBufferData
import org.positron.engine.core.scene.VertexBufferData.VertexBufferType

class Box(length: Float, width: Float, height: Float): Geometry() {

    companion object {
        private val boxIndices = shortArrayOf(
            0, 1, 2, // 0
            1, 3, 2,
            4, 6, 5, // 2
            5, 6, 7,
            0, 2, 4, // 4
            4, 2, 6,
            1, 5, 3, // 6
            5, 7, 3,
            0, 4, 1, // 8
            4, 5, 1,
            2, 3, 6, // 10
            6, 3, 7
        )
    }

    init {
        val halfLength = length / 2f
        val halfWidth = width / 2f
        val halfHeight = height / 2f

        val boxVertices = floatArrayOf(
            -halfLength,  halfHeight,  halfWidth,
             halfLength,  halfHeight,  halfWidth,
            -halfLength, -halfHeight,  halfWidth,
             halfLength, -halfHeight,  halfWidth,
            -halfLength,  halfHeight, -halfWidth,
             halfLength,  halfHeight, -halfWidth,
            -halfLength, -halfHeight, -halfWidth,
             halfLength, -halfHeight, -halfWidth
        )

        vertexBuffer = VertexBuffer(VertexBufferData(boxVertices, VertexBufferType.POSITION))
        indexBuffer = IndexBuffer(boxIndices)

        build()
    }
}