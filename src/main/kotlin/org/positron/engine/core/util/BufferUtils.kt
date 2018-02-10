package org.positron.engine.core.util

import org.joml.Vector4f
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer

class BufferUtils {
    companion object {

        fun create(value: Any): ByteBuffer {
            return when (value) {
                is Int -> this.create(intArrayOf(value))
                is IntArray -> this.create(value)
                is Float -> this.create(floatArrayOf(value))
                is FloatArray -> this.create(value)
                is ShortArray -> this.create(value)
                is Vector4f -> this.create(value)
                else -> throw IllegalArgumentException("Unknown value type of ${value.javaClass.name}")
            }
        }

        private const val FLOAT_SIZE = 4
        fun create(array: FloatArray): ByteBuffer {
            val buffer: ByteBuffer = MemoryUtil.memAlloc(array.size * FLOAT_SIZE)
            for (element in array) {
                buffer.putFloat(element)
            }
            buffer.flip()
            return buffer
        }

        private const val INT_SIZE = 4
        fun create(array: IntArray): ByteBuffer {
            val buffer: ByteBuffer = MemoryUtil.memAlloc(array.size * INT_SIZE)
            for (element in array) {
                buffer.putInt(element)
            }
            buffer.flip()
            return buffer
        }

        private const val SHORT_SIZE = 2
        fun create(array: ShortArray): ByteBuffer {
            val buffer: ByteBuffer = MemoryUtil.memAlloc(array.size * SHORT_SIZE)
            for (element in array) {
                buffer.putShort(element)
            }
            buffer.flip()
            return buffer
        }

        fun create(vec4: Vector4f): ByteBuffer {
            val buffer: ByteBuffer = MemoryUtil.memAlloc(4 * FLOAT_SIZE)
            buffer.putFloat(vec4.x)
            buffer.putFloat(vec4.y)
            buffer.putFloat(vec4.z)
            buffer.putFloat(vec4.w)
            buffer.flip()
            return buffer
        }

    }
}