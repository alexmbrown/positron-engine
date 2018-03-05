#if DEPTH_PACKING == 3200

	uniform float opacity;

#endif

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/packing.sh"
#include "../../ShaderChunk/uv_pars_fragment.sh"
#include "../../ShaderChunk/map_pars_fragment.sh"
#include "../../ShaderChunk/alphamap_pars_fragment.sh"
#include "../../ShaderChunk/logdepthbuf_pars_fragment.sh"
#include "../../ShaderChunk/clipping_planes_pars_fragment.sh"

void main() {

	#include "../../ShaderChunk/clipping_planes_fragment.sh"

	vec4 diffuseColor = vec4( 1.0 );

	#if DEPTH_PACKING == 3200

		diffuseColor.a = opacity;

	#endif

	#include "../../ShaderChunk/map_fragment.sh"
	#include "../../ShaderChunk/alphamap_fragment.sh"
	#include "../../ShaderChunk/alphatest_fragment.sh"

	#include "../../ShaderChunk/logdepthbuf_fragment.sh"

	#if DEPTH_PACKING == 3200

		gl_FragColor = vec4( vec3( 1.0 - gl_FragCoord.z ), opacity );

	#elif DEPTH_PACKING == 3201

		gl_FragColor = packDepthToRGBA( gl_FragCoord.z );

	#endif

}
