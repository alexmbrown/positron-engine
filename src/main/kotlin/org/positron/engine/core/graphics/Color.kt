package org.positron.engine.core.graphics

import org.joml.Vector4f

class Color {

    companion object {
        val BLACK: Color = Color(0f, 0f, 0f, 1f)
        val BLUE:  Color = Color(0f, 0f, 1f, 1f)
        val GREEN: Color = Color(0f, 1f, 0f, 1f)
        val RED:   Color = Color(1f, 0f, 0f, 1f)
        val WHITE: Color = Color(1f, 1f, 1f, 1f)
    }

    var r: Float
    var g: Float
    var b: Float
    var a: Float

    constructor(r: Float, g: Float, b: Float, a: Float) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun toVec4f(): Vector4f {
        return Vector4f(r, g, b, a)
    }

}