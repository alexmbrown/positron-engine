uniform vec4 diffuse;

#ifndef FLAT_SHADED

	varying vec3 vNormal;

#endif

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/color_pars_fragment.sh"
#include "../../ShaderChunk/uv_pars_fragment.sh"
#include "../../ShaderChunk/uv2_pars_fragment.sh"
#include "../../ShaderChunk/map_pars_fragment.sh"
#include "../../ShaderChunk/alphamap_pars_fragment.sh"
#include "../../ShaderChunk/aomap_pars_fragment.sh"
#include "../../ShaderChunk/lightmap_pars_fragment.sh"
#include "../../ShaderChunk/envmap_pars_fragment.sh"
#include "../../ShaderChunk/fog_pars_fragment.sh"
#include "../../ShaderChunk/specularmap_pars_fragment.sh"
#include "../../ShaderChunk/logdepthbuf_pars_fragment.sh"
#include "../../ShaderChunk/clipping_planes_pars_fragment.sh"

void main() {

	#include "../../ShaderChunk/clipping_planes_fragment.sh"

	#include "../../ShaderChunk/logdepthbuf_fragment.sh"
	#include "../../ShaderChunk/map_fragment.sh"
	#include "../../ShaderChunk/color_fragment.sh"
	#include "../../ShaderChunk/alphamap_fragment.sh"
	#include "../../ShaderChunk/alphatest_fragment.sh"
	#include "../../ShaderChunk/specularmap_fragment.sh"

	ReflectedLight reflectedLight = ReflectedLight( vec3( 0.0 ), vec3( 0.0 ), vec3( 0.0 ), vec3( 0.0 ) );

	// accumulation (baked indirect lighting only)
	#ifdef USE_LIGHTMAP

		reflectedLight.indirectDiffuse += texture2D( lightMap, vUv2 ).xyz * lightMapIntensity;

	#else

		reflectedLight.indirectDiffuse += vec3( 1.0 );

	#endif

	// modulation
	#include "../../ShaderChunk/aomap_fragment.sh"

	reflectedLight.indirectDiffuse *= diffuse.rgb;

	vec3 outgoingLight = reflectedLight.indirectDiffuse;

	#include "../../ShaderChunk/envmap_fragment.sh"

	gl_FragColor = vec4( outgoingLight, diffuse.a );

	#include "../../ShaderChunk/premultiplied_alpha_fragment.sh"
	#include "../../ShaderChunk/tonemapping_fragment.sh"
	#include "../../ShaderChunk/encodings_fragment.sh"
	#include "../../ShaderChunk/fog_fragment.sh"

}
