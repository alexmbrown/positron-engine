package org.positron.engine.core.mesh.material

import org.positron.engine.core.shader.ShaderProgram

abstract class Material(val program: ShaderProgram, val uniforms: MutableList<Uniform>) {

    fun getUniform(name: String): Uniform? {
        return uniforms.filter { u -> u.name === name }.first()
    }

    fun setUniform(name: String, value: Any) {
        val uniform = getUniform(name)
        if (uniform != null) {
            uniform.updateValue(value)
        } else {
            throw IllegalArgumentException("Uniform with name '$name' does nto exist")
        }
    }

}