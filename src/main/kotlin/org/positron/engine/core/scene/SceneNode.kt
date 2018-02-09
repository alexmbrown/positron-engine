package org.positron.engine.core.scene

// parent allowed to be null for root only
open class SceneNode(var parent: SceneNode?) {

    val children: MutableList<SceneNode> = ArrayList()

}