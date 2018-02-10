package org.positron.engine.core.mesh.shapes

import org.positron.engine.core.mesh.Geometry
import org.positron.engine.core.scene.IndexBuffer
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.scene.VertexBufferData
import org.positron.engine.core.scene.VertexBufferData.VertexBufferType

class Box: Geometry {

    private val indices = mutableListOf<Short>()
    private val vertices = mutableListOf<Float>()
    private val normals = mutableListOf<Float>()
    private val uvs = mutableListOf<Float>()

    var numberOfVertices = 0

    constructor(width: Float, height: Float, depth: Float): this(width, height, depth, 1, 1, 1)

    constructor(width: Float, height: Float, depth: Float, widthSegments: Int, heightSegments: Int, depthSegments: Int) {
        // build each side of the box geometry
        buildPlane(2, 1, 0, - 1f, - 1f, depth, height, width, depthSegments, heightSegments)   // px
        buildPlane(2, 1, 0, 1f, - 1f, depth, height, - width, depthSegments, heightSegments)   // nx
        buildPlane(0, 2, 1, 1f, 1f, width, depth, height, widthSegments, depthSegments)        // py
        buildPlane(0, 2, 1, 1f, - 1f, width, depth, - height, widthSegments, depthSegments)    // ny
        buildPlane(0, 1, 2, 1f, - 1f, width, height, depth, widthSegments, heightSegments)     // pz
        buildPlane(0, 1, 2, - 1f, - 1f, width, height, - depth, widthSegments, heightSegments) // nz

        // build geometry
        indexBuffer = IndexBuffer(indices.toShortArray())
        vertexBuffer = VertexBuffer(
            VertexBufferData(vertices.toFloatArray(), VertexBufferType.POSITION),
            VertexBufferData(normals.toFloatArray(), VertexBufferType.NORMAL),
            VertexBufferData(uvs.toFloatArray(), VertexBufferType.TEXTURE)
        )

        build()
    }

    private fun buildPlane(
        u: Int,
        v: Int,
        w: Int,
        udir: Float,
        vdir: Float,
        width: Float,
        height: Float,
        depth: Float,
        gridX: Int,
        gridY: Int
    ) {
        val segmentWidth = width / gridX
        val segmentHeight = height / gridY

        val widthHalf = width / 2f
        val heightHalf = height / 2f
        val depthHalf = depth / 2f

        val gridX1 = gridX + 1
        val gridY1 = gridY + 1

        var vertexCounter = 0
        var groupCount = 0

        val vector = FloatArray(3)

        // generate vertices, normals and uvs

        for (iy in 0 until gridY1) {

            val y = iy * segmentHeight - heightHalf
            for (ix in 0 until gridX1) {

                val x = ix * segmentWidth - widthHalf

                // set values to correct vector component
                vector[u] = x * udir
                vector[v] = y * vdir
                vector[w] = depthHalf

                // now apply vector to vertex buffer
                vertices.add(vector[0])
                vertices.add(vector[1])
                vertices.add(vector[2])

                // set values to correct vector component
                vector[u] = 0f
                vector[v] = 0f
                vector[w] = if (depth > 0) 1f else - 1f

                // now apply vector to normal buffer
                normals.add(vector[0])
                normals.add(vector[1])
                normals.add(vector[2])

                // uvs
                uvs.add(ix.toFloat() / gridX)
                uvs.add(1f - (iy.toFloat() / gridY ))

                // counters
                vertexCounter += 1
            }

        }

        // indices
        for (iy in 0 until gridY) {

            for (ix in 0 until gridX) {

                val a = numberOfVertices + ix + gridX1 * iy
                val b = numberOfVertices + ix + gridX1 * ( iy + 1 )
                val c = numberOfVertices + ( ix + 1 ) + gridX1 * ( iy + 1 )
                val d = numberOfVertices + ( ix + 1 ) + gridX1 * iy

                // faces
                indices.add(a.toShort())
                indices.add(b.toShort())
                indices.add(d.toShort())
                indices.add(b.toShort())
                indices.add(c.toShort())
                indices.add(d.toShort())

                // increase counter
                groupCount += 6

            }

        }

        // update total number of vertices
        numberOfVertices += vertexCounter

    }
}