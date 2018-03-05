package org.positron.engine.core.scene.light

import org.positron.engine.core.graphics.Color

class AmbientLight: Light {

    constructor(): super()

    constructor(color: Color): super(color)

}