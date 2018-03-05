package org.positron.engine.core.shader

import org.positron.engine.core.util.ResourceUtils

class Shaders {
    companion object {

        lateinit var MESH_BASIC: ShaderProgram
//        lateinit var PHONG: ShaderProgram

        fun init() {
            MESH_BASIC = ShaderProgram(
                Shader(ShaderType.VERTEX, ResourceUtils.load("/shaders/mesh_basic", "vs_mesh_basic.bin")),
                Shader(ShaderType.FRAGMENT, ResourceUtils.load("/shaders/mesh_basic", "fs_mesh_basic.bin"))
            )
//            PHONG = ShaderProgram(
//                Shader(ShaderType.VERTEX, ResourceUtils.load("/shaders", "vs_phong.bin")),
//                Shader(ShaderType.FRAGMENT, ResourceUtils.load("/shaders", "fs_phong.bin"))
//            )
        }

        fun destroy() {
            MESH_BASIC.destroy()
        }

    }
}