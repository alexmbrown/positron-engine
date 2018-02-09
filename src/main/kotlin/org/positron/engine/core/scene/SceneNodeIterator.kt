package org.positron.engine.core.scene

import org.joml.Matrix4f
import java.util.Stack

class SceneNodeIterator(root: SceneNode) {

    val model = Stack<Matrix4f>()
    var complete = false
    var current = root

    init {
        val mat = Matrix4f()
        mat.identity()
        model.push(mat)
    }

    fun next(cb: (SceneNode, Matrix4f) -> Unit) {
        getNext()
        if (!complete) {
            pushSpatial(current)
        }
        cb(current, model.peek())
    }

    private fun getNext() {
        // at root
        if (current.parent == null) {
            // without children
            return if (current.children.isEmpty()) {
                complete = true
            }
            // with children
            else {
                current = current.children[0]
            }
        }
        // not at root
        else {
            var parent = current.parent!!
            var index = getIndex(current)
            // go down to first child
            if (!current.children.isEmpty()) {
                current = current.children[0]
            }
            // has next sibling
            else if (index < parent.children.size - 2) {
                model.pop()
                current = parent.children[index + 1]
            }
            // has no next sibling
            else {
                // while not last child
                while(current.parent != null && index >= current.parent?.children?.size!! - 1) {
                    model.pop()
                    current = parent
                    index = getIndex(current)
                }
                if (current.parent == null) {
                    complete = true
                }
                // move to next sibling
                else {
                    current = parent.children[index + 1]
                }
            }
        }

    }

    private fun pushSpatial(sceneNode: SceneNode) {
        var mat = Matrix4f()
        if (sceneNode is Spatial) {
            mat = sceneNode.model
        }
        val top = model.peek()
        top.mul(mat)
        model.push(top)
    }

    private fun getIndex(sceneNode: SceneNode): Int {
        if (sceneNode.parent == null) {
            return -1
        }
        return sceneNode.parent?.children?.indexOf(sceneNode)!!
    }

}