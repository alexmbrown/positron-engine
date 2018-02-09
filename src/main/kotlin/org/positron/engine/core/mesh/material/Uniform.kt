package org.positron.engine.core.mesh.material

import org.lwjgl.bgfx.BGFX
import org.positron.engine.core.util.BufferUtils
import java.nio.ByteBuffer

class Uniform(val name: String, value: Any) {

    companion object {
        const val COLOR = "u_color"
    }

    enum class UniformType(val type: Int, val num: Int) {
        INT(BGFX.BGFX_UNIFORM_TYPE_INT1, 1),
        VEC4(BGFX.BGFX_UNIFORM_TYPE_VEC4, 4)
    }

    var type: UniformType
        private set

    private var ref: Short

    // values
    private var valueBuffer: ByteBuffer

    init {
        valueBuffer = BufferUtils.Companion.create(value)
        type = UniformType.INT
        ref = BGFX.bgfx_create_uniform(name, type.type, type.num)
    }

    fun updateValue(value: Any) {
        valueBuffer = BufferUtils.Companion.create(value)
    }

    fun bind(encoder: Long) {
        BGFX.bgfx_encoder_set_uniform(encoder, ref, valueBuffer, type.num)
    }


}