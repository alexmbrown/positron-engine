package org.positron.engine.core.scene

import org.lwjgl.bgfx.BGFX
import org.lwjgl.bgfx.BGFXMemory
import org.lwjgl.system.MemoryUtil
import org.positron.engine.core.util.BufferUtils
import java.nio.ByteBuffer

class IndexBuffer(data: ShortArray) {

    var faceCount = 0
        private set

    var memoryRef: BGFXMemory
    var ref: Short? = null

    private var buffer: ByteBuffer

    init {
        if (data.size % 3 != 0) {
            throw IllegalStateException("Only triangular faces are supported")
        }

        buffer = BufferUtils.create(data)
        memoryRef = BGFX.bgfx_make_ref(buffer)
        faceCount = data.size / 3
    }

    fun destroy() {
        BGFX.bgfx_destroy_index_buffer(ref!!)
        MemoryUtil.memFree(buffer)
    }

}