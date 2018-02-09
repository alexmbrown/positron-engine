package org.positron.engine.core.shader

import org.lwjgl.bgfx.BGFX

class ShaderProgram(val vertexShader: Shader, val fragmentShader: Shader) {

    var ref: Short = BGFX.bgfx_create_program(vertexShader.ref, fragmentShader.ref, true)

    fun destroy() {
        vertexShader.destroy()
        fragmentShader.destroy()
        BGFX.bgfx_destroy_program(ref)
    }

}