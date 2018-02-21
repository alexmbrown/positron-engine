package org.positron.engine.core.mesh.geometries

class ConeGeometry: CylinderGeometry {

    constructor(radius: Float, height: Float): super(radius, 0f, height)

    constructor(radius: Float, height: Float, radialSegments: Int, heightSegments: Int): super(radius, 0f, height, radialSegments, heightSegments)

    constructor(radius: Float, height: Float, radialSegments: Int, heightSegments: Int, openEnded: Boolean): super(radius, 0f, height, radialSegments, heightSegments, openEnded)

    constructor(radius: Float, height: Float, radialSegments: Int, heightSegments: Int, openEnded: Boolean, thetaStart: Float, thetaLength: Float): super(radius, 0f, height, radialSegments, heightSegments, openEnded, thetaStart, thetaLength)

}