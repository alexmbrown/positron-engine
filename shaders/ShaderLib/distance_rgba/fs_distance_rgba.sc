#define DISTANCE

uniform vec3 referencePosition;
uniform float nearDistance;
uniform float farDistance;
varying vec3 vWorldPosition;

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/packing.sh"
#include "../../ShaderChunk/uv_pars_fragment.sh"
#include "../../ShaderChunk/map_pars_fragment.sh"
#include "../../ShaderChunk/alphamap_pars_fragment.sh"
#include "../../ShaderChunk/clipping_planes_pars_fragment.sh"

void main () {

	#include "../../ShaderChunk/clipping_planes_fragment.sh"

	vec4 diffuseColor = vec4( 1.0 );

	#include "../../ShaderChunk/map_fragment.sh"
	#include "../../ShaderChunk/alphamap_fragment.sh"
	#include "../../ShaderChunk/alphatest_fragment.sh"

	float dist = length( vWorldPosition - referencePosition );
	dist = ( dist - nearDistance ) / ( farDistance - nearDistance );
	dist = saturate( dist ); // clamp to [ 0, 1 ]

	gl_FragColor = packDepthToRGBA( dist );

}
