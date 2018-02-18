package org.positron.engine.core.mesh

import org.lwjgl.bgfx.BGFX
import org.positron.engine.core.scene.VertexBuffer
import org.lwjgl.bgfx.BGFXVertexDecl
import org.positron.engine.core.renderer.Renderer
import org.positron.engine.core.scene.IndexBuffer

open class Geometry {

    var vertexBuffer: VertexBuffer? = null
    var indexBuffer: IndexBuffer? = null

    private val vertexDecl = BGFXVertexDecl.calloc()

    private var initialized: Boolean = false

    protected fun build() {
        if (vertexBuffer == null || indexBuffer == null) {
            throw IllegalStateException("At least one vertex buffer and an index buffer must be defined.")
        }

        val vBuffer: VertexBuffer = vertexBuffer as VertexBuffer
        val iBuffer: IndexBuffer = indexBuffer as IndexBuffer

        // Vertex Buffer
        BGFX.bgfx_vertex_decl_begin(vertexDecl, Renderer.MAIN_RENDERER)
        vBuffer.types.forEach { BGFX.bgfx_vertex_decl_add(vertexDecl, it.attrib, it.num, it.type, it.normalized, false) }
        BGFX.bgfx_vertex_decl_end(vertexDecl)
        vBuffer.ref =  BGFX.bgfx_create_vertex_buffer(vBuffer.memeoryRef, vertexDecl, BGFX.BGFX_BUFFER_NONE.toInt())

        // Index Buffer
        iBuffer.ref = BGFX.bgfx_create_index_buffer(iBuffer.memoryRef, BGFX.BGFX_BUFFER_NONE.toInt())

        initialized = true
    }

    fun destroy() {
        if (initialized && vertexBuffer != null || indexBuffer != null) {
            vertexBuffer!!.destroy()
            indexBuffer!!.destroy()

            vertexDecl.free()
        }
    }

}