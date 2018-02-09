package org.positron.engine.core.shader

import org.lwjgl.bgfx.BGFX
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.NULL
import java.nio.ByteBuffer

class Shader(val type: ShaderType, code: ByteBuffer) {

    var ref: Short = -1
        private set

    init {
        val release = BGFX.bgfx_make_ref_release(code, { ptr, _ -> MemoryUtil.nmemFree(ptr) }, NULL)
        ref = BGFX.bgfx_create_shader(release)
    }

    fun destroy() {
        BGFX.bgfx_destroy_shader(ref)
    }

}