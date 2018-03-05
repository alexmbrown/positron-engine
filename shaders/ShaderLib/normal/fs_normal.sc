#define NORMAL

uniform float opacity;

#if defined( FLAT_SHADED ) || defined( USE_BUMPMAP ) || defined( USE_NORMALMAP )

	varying vec3 vViewPosition;

#endif

#ifndef FLAT_SHADED

	varying vec3 vNormal;

#endif

#include "../../ShaderChunk/packing.sh"
#include "../../ShaderChunk/uv_pars_fragment.sh"
#include "../../ShaderChunk/bumpmap_pars_fragment.sh"
#include "../../ShaderChunk/normalmap_pars_fragment.sh"
#include "../../ShaderChunk/logdepthbuf_pars_fragment.sh"

void main() {

	#include "../../ShaderChunk/logdepthbuf_fragment.sh"
	#include "../../ShaderChunk/normal_fragment_begin.sh"
	#include "../../ShaderChunk/normal_fragment_maps.sh"

	gl_FragColor = vec4( packNormalToRGB( normal ), opacity );

}
