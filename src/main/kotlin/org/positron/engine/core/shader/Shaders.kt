package org.positron.engine.core.shader

import org.positron.engine.core.util.ResourceUtils

class Shaders {
    companion object {

        lateinit var BASIC: ShaderProgram

        fun init() {
            BASIC = ShaderProgram(
                Shader(ShaderType.VERTEX, ResourceUtils.load("/shaders", "vs_basic.bin")),
                Shader(ShaderType.FRAGMENT, ResourceUtils.load("/shaders", "fs_basic.bin"))
            )
        }

        fun destroy() {
            BASIC.destroy()
        }

    }
}