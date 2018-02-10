package org.positron.engine.core.mesh.material

import org.joml.Vector4f
import org.lwjgl.bgfx.BGFX
import org.positron.engine.core.util.BufferUtils
import java.nio.ByteBuffer

class Uniform(val name: String, value: Any) {

    enum class UniformType(val type: Int, val num: Int) {
        INT(BGFX.BGFX_UNIFORM_TYPE_INT1, 1),
        VEC4(BGFX.BGFX_UNIFORM_TYPE_INT1, 4),
        UNKNOWN(-1, -1)
    }

    var type: UniformType
        private set

    private var ref: Short

    // values
    private var valueBuffer: ByteBuffer

    init {
        valueBuffer = BufferUtils.Companion.create(value)

        type = when (value) {
            is Int -> UniformType.INT
            is Vector4f -> UniformType.VEC4
            else -> UniformType.UNKNOWN
        }

        ref = BGFX.bgfx_create_uniform(name, type.type, type.num)
    }

    fun updateValue(value: Any) {
        valueBuffer = BufferUtils.Companion.create(value)
    }

    fun bind(encoder: Long) {
        BGFX.bgfx_encoder_set_uniform(encoder, ref, valueBuffer, type.num)
    }


}