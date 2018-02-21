package org.positron.engine.core.mesh.geometries

import org.positron.engine.core.mesh.Geometry
import org.positron.engine.core.scene.IndexBuffer
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.scene.VertexBufferData

class PlaneGeometry : Geometry {

    constructor(width: Float, height: Float): this(width, height, 1, 1)

    constructor(width: Float, height: Float, widthSegments: Int, heightSegments: Int) {

        val widthHalf = width / 2
        val heightHalf = height / 2

        val gridX = widthSegments
        val gridY = heightSegments

        var gridX1 = gridX + 1
        var gridY1 = gridY + 1

        val segmentWidth = width / gridX
        val segmentHeight = height / gridY

        // buffers
        val indices = mutableListOf<Short>()
        val vertices = mutableListOf<Float>()
        val normals = mutableListOf<Float>()
        val uvs = mutableListOf<Float>()

        // generate vertices, normals and uvs
        for (iy in 0 until gridY1) {

            val y = iy * segmentHeight - heightHalf

            for (ix in 0 until gridX1) {

                val x = ix * segmentWidth - widthHalf

                vertices.add(x)
                vertices.add(-y)
                vertices.add(0f)

                normals.add(0f)
                normals.add(0f)
                normals.add(1f)

                uvs.add(ix.toFloat() / gridX)
                uvs.add(1f - (iy.toFloat() / gridY))

            }

        }

        // indices
        for (iy in 0 until gridY) {

            for (ix in 0 until gridX) {

                val a = ix + gridX1 * iy
                val b = ix + gridX1 * ( iy + 1 )
                val c = ( ix + 1 ) + gridX1 * ( iy + 1 )
                val d = ( ix + 1 ) + gridX1 * iy

                // faces
                indices.add(a.toShort())
                indices.add(b.toShort())
                indices.add(d.toShort())
                indices.add(b.toShort())
                indices.add(c.toShort())
                indices.add(d.toShort())

            }

        }


        indexBuffer = IndexBuffer(indices.toShortArray())
        vertexBuffer = VertexBuffer(
                VertexBufferData(vertices.toFloatArray(), VertexBufferData.VertexBufferType.POSITION),
                VertexBufferData(normals.toFloatArray(), VertexBufferData.VertexBufferType.NORMAL),
                VertexBufferData(uvs.toFloatArray(), VertexBufferData.VertexBufferType.TEXTURE)
        )

        build()
    }

}