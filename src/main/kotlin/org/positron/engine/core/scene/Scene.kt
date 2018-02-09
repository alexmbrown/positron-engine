package org.positron.engine.core.scene

class Scene {

    var root = SceneNode(null)

    fun add(node: SceneNode) {
        node.parent = root
        root.children.add(node)
    }

    fun iterate(): SceneNodeIterator {
        return SceneNodeIterator(root)
    }

}