package org.positron.engine.core.scene

import org.positron.engine.core.scene.light.Light

class Scene {

    var root = SceneNode(null)
    var lights: MutableList<Light> = ArrayList()

    fun add(node: SceneNode) {
        node.parent = root
        root.children.add(node)
    }

    fun add(light: Light) {
        lights.add(light)
    }

    fun iterate(): SceneNodeIterator {
        return SceneNodeIterator(root)
    }

}