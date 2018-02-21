package org.positron.engine.core.mesh.geometries

import org.joml.Vector3f
import org.positron.engine.core.mesh.Geometry
import org.positron.engine.core.scene.IndexBuffer
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.scene.VertexBufferData
import org.positron.engine.core.util.MathUtils

class SphereGeometry : Geometry {

    constructor(radius: Float): this(radius, 8, 6, 0f, MathUtils.PI * 2f, 0f, MathUtils.PI)

    constructor(radius: Float, widthSegments: Int, heightSegments: Int, phiStart: Float, phiLength: Float, thetaStart: Float, thetaLength: Float) {

        val wSegments = Math.max(3, widthSegments)
        val hSegments = Math.max(2, heightSegments)

        val thetaEnd = thetaStart + thetaLength

        var index = 0

        val grid = mutableListOf<List<Short>>()
        val vertex = mutableListOf(0f, 0f, 0f)
        val normal = Vector3f()

        // buffers
        val indices = mutableListOf<Short>()
        val vertices = mutableListOf<Float>()
        val normals = mutableListOf<Float>()
        val uvs = mutableListOf<Float>()

        // generate vertices, normals and uvs
        for (iy in 0..heightSegments) {

            val verticesRow = mutableListOf<Short>()

            val v = iy / heightSegments.toFloat()

            for (ix in 0..widthSegments) {

                val u = ix.toFloat() / widthSegments

                // vertex
                vertex[0] = -radius * MathUtils.cos(phiStart + u * phiLength) * MathUtils.sin( thetaStart + v * thetaLength)
                vertex[1] = radius * MathUtils.cos(thetaStart + v * thetaLength)
                vertex[2] = radius * MathUtils.sin(phiStart + u * phiLength) * MathUtils.sin(thetaStart + v * thetaLength)

                vertices.add(vertex[0])
                vertices.add(vertex[1])
                vertices.add(vertex[2])

                // normal
                normal.set(vertex[0], vertex[1], vertex[2]).normalize()
                normals.add(normal.x)
                normals.add(normal.y)
                normals.add(normal.y)

                // uv
                uvs.add(u)
                uvs.add(1f - v)

                verticesRow.add(index.toShort())
                index++

            }

            grid.add(verticesRow)

        }

        // indices
        for (iy in 0 until heightSegments) {
            for (ix in 0 until widthSegments) {

                val a = grid[iy][ix + 1]
                val b = grid[iy][ix]
                val c = grid[iy + 1][ix]
                val d = grid[iy + 1][ix + 1]

                if ( iy != 0 || thetaStart > 0 ) {
                    indices.add(a)
                    indices.add(b)
                    indices.add(d)
                }
                if ( iy != heightSegments - 1 || thetaEnd < Math.PI ) {
                    indices.add(b)
                    indices.add(c)
                    indices.add(d)
                }
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
