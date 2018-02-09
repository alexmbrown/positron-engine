package org.positron.engine.core.scene

import org.lwjgl.bgfx.BGFX
import org.positron.engine.core.util.BGFXUtils
import java.nio.ByteBuffer

class VertexBufferData {

    companion object {
        private const val UNKNOWN = -1
        private const val FLOAT_TYPE = 0
        private const val INT_TYPE = 1
    }

    enum class VertexBufferType(val attrib: Int, val type: Int, val stride: Int, val num: Int, val normalized: Boolean) {
        COLOR(BGFX.BGFX_ATTRIB_COLOR0, BGFX.BGFX_ATTRIB_TYPE_UINT8, 1, 4, true),
        POSITION(BGFX.BGFX_ATTRIB_POSITION, BGFX.BGFX_ATTRIB_TYPE_FLOAT, 3, 3, false),
        NORMAL(BGFX.BGFX_ATTRIB_NORMAL, BGFX.BGFX_ATTRIB_TYPE_FLOAT, 3, 3, false)
    }

    lateinit var types: Array<out VertexBufferType>

    var totalStride: Int = 0
        private set

    var byteLength: Int = 0
        private set

    var vertCount: Int = 0
        private set

    private var dataType: Int = UNKNOWN
    private lateinit var floatData: FloatArray
    private lateinit var intData: IntArray

    constructor(data: FloatArray, vararg types: VertexBufferType) {
        verify(types)
        vertCount = data.size / totalStride
        floatData = data
        dataType = FLOAT_TYPE
    }

    constructor(data: IntArray, vararg types: VertexBufferType) {
        verify(types)
        vertCount = data.size / totalStride
        intData = data
        dataType = INT_TYPE
    }

    private fun verify(types: Array<out VertexBufferType>) {
        if (types.isEmpty()) {
            throw IllegalStateException("Must contain vertex type")
        }

        for(type in types) {
            totalStride += type.stride
            byteLength += type.stride * BGFXUtils.attribByteLength(type.attrib)
        }
        this.types = types
    }

    fun push(buffer: ByteBuffer, index: Int) {
        for (i in 0 until totalStride) {
            when (dataType) {
                FLOAT_TYPE -> buffer.putFloat(floatData[index * totalStride + i])
                INT_TYPE -> buffer.putInt(intData[index * totalStride + i])
                else -> throw IllegalStateException("Unknown data type")
            }
        }
    }


}