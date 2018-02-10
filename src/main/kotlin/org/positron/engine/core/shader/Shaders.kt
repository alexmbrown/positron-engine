package org.positron.engine.core.shader

import org.positron.engine.core.util.ResourceUtils

class Shaders {
    companion object {

        lateinit var BASIC: ShaderProgram
        lateinit var WIREFRAME: ShaderProgram

        fun init() {
            BASIC = ShaderProgram(
                Shader(ShaderType.VERTEX, ResourceUtils.load("/shaders", "vs_basic.bin")),
                Shader(ShaderType.FRAGMENT, ResourceUtils.load("/shaders", "fs_basic.bin"))
            )
            WIREFRAME = ShaderProgram(
                Shader(ShaderType.VERTEX, ResourceUtils.load("/shaders", "vs_wireframe.bin")),
                Shader(ShaderType.FRAGMENT, ResourceUtils.load("/shaders", "fs_wireframe.bin"))
            )
        }

        fun destroy() {
            BASIC.destroy()
        }

    }
}