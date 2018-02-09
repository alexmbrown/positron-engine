package org.positron.engine.core.scene

import org.lwjgl.bgfx.BGFX
import org.lwjgl.bgfx.BGFX.bgfx_make_ref
import org.lwjgl.bgfx.BGFXMemory
import org.lwjgl.system.MemoryUtil
import org.positron.engine.core.scene.VertexBufferData.VertexBufferType
import java.nio.ByteBuffer

class VertexBuffer(vararg data: VertexBufferData) {

    var byteLength: Int = 0
        private set

    var vertCount: Int = 0
        private set

    var ref: Short? = null

    var types = mutableListOf<VertexBufferType>()
    var memeoryRef: BGFXMemory

    private lateinit var buffer: ByteBuffer

    init {
        // contains no data
        if (data.isEmpty()) {
            throw IllegalStateException("Must contain vertex data")
        }

        // inconsistent number of vertices
        vertCount = data[0].vertCount
        data
            .filter { it.vertCount != vertCount }
            .forEach { throw IllegalStateException("All data must contain the same number of vertices") }

        // calculate total byteLength and store types
        for (vData in data) {
            byteLength += vData.byteLength
            for(type in vData.types) {
                types.add(type)
            }
        }

        buffer = MemoryUtil.memAlloc(byteLength * vertCount)

        for (index in 0 until vertCount) {
            for (vData in data) {
                vData.push(buffer, index)
            }
        }

        buffer.flip()

        memeoryRef = bgfx_make_ref(buffer)
    }

    fun destroy() {
        BGFX.bgfx_destroy_vertex_buffer(ref!!)
        MemoryUtil.memFree(buffer)
    }

}