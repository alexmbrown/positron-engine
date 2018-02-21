package org.positron.engine.core.util

class MathUtils {
    companion object {

        const val PI = Math.PI.toFloat()
        const val PI2 = Math.PI.toFloat() * 2f
        const val DEG2RAD = MathUtils.PI / 180f

        fun floor(value: Float): Float {
            return Math.floor(value.toDouble()).toFloat()
        }

        fun cos(value: Float): Float {
            return Math.cos(value.toDouble()).toFloat()
        }

        fun sin(value: Float): Float {
            return Math.sin(value.toDouble()).toFloat()
        }
    }
}