package org.positron.engine.core.util

import org.lwjgl.bgfx.BGFX
import java.security.InvalidParameterException

class BGFXUtils {
    companion object {

        fun attribByteLength(attrib: Int): Int {
            return when (attrib) {
                BGFX.BGFX_ATTRIB_TYPE_UINT8 -> 4
                BGFX.BGFX_ATTRIB_TYPE_FLOAT -> 4
                else -> throw InvalidParameterException()
            }
        }

        val zZeroToOne = !BGFX.bgfx_get_caps().homogeneousDepth()

    }
}