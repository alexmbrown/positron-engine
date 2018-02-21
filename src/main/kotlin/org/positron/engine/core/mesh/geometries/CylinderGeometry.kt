package org.positron.engine.core.mesh.geometries

import org.joml.Vector3f
import org.positron.engine.core.mesh.Geometry
import org.positron.engine.core.scene.IndexBuffer
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.scene.VertexBufferData
import org.positron.engine.core.util.MathUtils

open class CylinderGeometry : Geometry {

    private val indices = mutableListOf<Short>()
    private val vertices = mutableListOf<Float>()
    private val normals = mutableListOf<Float>()
    private val uvs = mutableListOf<Float>()
    private var index: Short = 0

    constructor(radius: Float, height: Float): this(radius, radius, height)

    constructor(radiusTop: Float, radiusBottom: Float, height: Float): this(radiusTop, radiusBottom, height, 8, 1, false, 0f, MathUtils.PI * 2f)

    constructor(radiusTop: Float, radiusBottom: Float, height: Float, radialSegments: Int, heightSegments: Int): this(radiusTop, radiusBottom, height, radialSegments, heightSegments, false, 0f, MathUtils.PI2)

    constructor(radiusTop: Float, radiusBottom: Float, height: Float, radialSegments: Int, heightSegments: Int, openEnded: Boolean): this(radiusTop, radiusBottom, height, radialSegments, heightSegments, openEnded, 0f, MathUtils.PI2)

    constructor(radiusTop: Float, radiusBottom: Float, height: Float, radialSegments: Int, heightSegments: Int, openEnded: Boolean, thetaStart: Float, thetaLength: Float) {
        // generate geometry
        generateTorso(radiusTop, radiusBottom, height, radialSegments, heightSegments, thetaStart, thetaLength)

        if (!openEnded) {
            if (radiusTop > 0) {
                generateCap(true, radiusTop, radiusBottom, height, radialSegments, thetaLength, thetaStart)
            }
            if (radiusBottom > 0) {
                generateCap(false, radiusTop, radiusBottom, height, radialSegments, thetaLength, thetaStart)
            }
        }

        // build geometry
        indexBuffer = IndexBuffer(indices.toShortArray())
        vertexBuffer = VertexBuffer(
            VertexBufferData(vertices.toFloatArray(), VertexBufferData.VertexBufferType.POSITION),
            VertexBufferData(normals.toFloatArray(), VertexBufferData.VertexBufferType.NORMAL),
            VertexBufferData(uvs.toFloatArray(), VertexBufferData.VertexBufferType.TEXTURE)
        )

        build()
    }

    private fun generateTorso(radiusTop: Float, radiusBottom: Float, height: Float, radialSegments: Int, heightSegments: Int, thetaStart: Float, thetaLength: Float) {
        val halfHeight = height / 2f

        val normal = Vector3f()
        val vertex = floatArrayOf(0f, 0f, 0f)
        val indexArray = mutableListOf<MutableList<Short>>()

        // this will be used to calculate the normal
        val slope = ( radiusBottom - radiusTop ) / height

        // generate vertices, normals and uvs
        for (y in 0..heightSegments) {

            val indexRow = mutableListOf<Short>()
            val v = y.toFloat() / heightSegments

            // calculate the radius of the current row
            val radius = v * (radiusBottom - radiusTop) + radiusTop

            for (x in 0..radialSegments) {

                val u = x.toFloat() / radialSegments

                val theta = u * thetaLength + thetaStart

                val sinTheta = MathUtils.sin(theta)
                val cosTheta = MathUtils.cos(theta)

                // vertex
                vertex[0] = radius * sinTheta
                vertex[1] = - v * height + halfHeight
                vertex[2] = radius * cosTheta
                vertices.add(vertex[2])
                vertices.add(vertex[1])
                vertices.add(vertex[0])

                // normal
                normal.set(sinTheta, slope, cosTheta).normalize()
                normals.add(normal.x)
                normals.add(normal.y)
                normals.add(normal.z)

                // uv
                uvs.add(u)
                uvs.add(1 - v)

                // save index of vertex in respective row
                indexRow.add(index)
                index++
            }

            // now save vertices of the row in our index array
            indexArray.add(indexRow)
        }

        // generate indices
        for (x in 0 until radialSegments) {
            for (y in 0 until heightSegments) {
                // we use the index array to access the correct indices
                val a = indexArray[y][x]
                val b = indexArray[y + 1][x]
                val c = indexArray[y + 1][x + 1]
                val d = indexArray[y][x + 1]

                // faces
                indices.add(a)
                indices.add(b)
                indices.add(d)
                indices.add(b)
                indices.add(c)
                indices.add(d)
            }

        }
    }

    private fun generateCap(top :Boolean, radiusTop: Float, radiusBottom: Float, height: Float, radialSegments: Int, thetaLength: Float, thetaStart: Float) {
        val halfHeight = height / 2f

        val radius = if (top) radiusTop else radiusBottom
        val sign = if (top)  1 else - 1

        val centerIndexStart = index

        for (x in 1..radialSegments) {
            // vertex
            vertices.add(0f)
            vertices.add(halfHeight * sign)
            vertices.add(0f)

            // normal
            normals.add(0f)
            normals.add(sign.toFloat())
            normals.add(0f)

            // uv
            uvs.add(0.5f)
            uvs.add(0.5f)

            // increase index
            index++
        }

        // save the index of the last center vertex
        val centerIndexEnd = index

        // now we generate the surrounding vertices, normals and uvs
        for (x in 0..radialSegments) {

            val u = x.toFloat() / radialSegments.toFloat()
            val theta = u * thetaLength + thetaStart

            val cosTheta = MathUtils.cos(theta)
            val sinTheta = MathUtils.sin(theta)

            // vertex
            vertices.add(radius * sinTheta)
            vertices.add(halfHeight * sign)
            vertices.add(radius * cosTheta)

            // normal
            normals.add(0f)
            normals.add(sign.toFloat())
            normals.add(0f)

            // uv
            uvs.add((cosTheta * 0.5f) + 0.5f)
            uvs.add((sinTheta * 0.5f * sign) + 0.5f)

            // increase index
            index++
        }

        // generate indices
        for (x in 0 until radialSegments) {

            val c = (centerIndexStart + x).toShort()
            val i = (centerIndexEnd + x).toShort()

            if (top) {
                // face top
                indices.add(c)
                indices.add((i + 1).toShort())
                indices.add(i)
            } else {
                // face bottom
                indices.add(c)
                indices.add(i)
                indices.add((i + 1).toShort())
            }
        }
    }

}