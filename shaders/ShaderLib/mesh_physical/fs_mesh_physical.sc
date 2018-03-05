#define PHYSICAL

uniform vec3 diffuse;
uniform vec3 emissive;
uniform float roughness;
uniform float metalness;
uniform float opacity;

#ifndef STANDARD
	uniform float clearCoat;
	uniform float clearCoatRoughness;
#endif

varying vec3 vViewPosition;

#ifndef FLAT_SHADED

	varying vec3 vNormal;

#endif

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/packing.sh"
#include "../../ShaderChunk/dithering_pars_fragment.sh"
#include "../../ShaderChunk/color_pars_fragment.sh"
#include "../../ShaderChunk/uv_pars_fragment.sh"
#include "../../ShaderChunk/uv2_pars_fragment.sh"
#include "../../ShaderChunk/map_pars_fragment.sh"
#include "../../ShaderChunk/alphamap_pars_fragment.sh"
#include "../../ShaderChunk/aomap_pars_fragment.sh"
#include "../../ShaderChunk/lightmap_pars_fragment.sh"
#include "../../ShaderChunk/emissivemap_pars_fragment.sh"
#include "../../ShaderChunk/envmap_pars_fragment.sh"
#include "../../ShaderChunk/fog_pars_fragment.sh"
#include "../../ShaderChunk/bsdfs.sh"
#include "../../ShaderChunk/cube_uv_reflection_fragment.sh"
#include "../../ShaderChunk/lights_pars_begin.sh"
#include "../../ShaderChunk/lights_pars_maps.sh"
#include "../../ShaderChunk/lights_physical_pars_fragment.sh"
#include "../../ShaderChunk/shadowmap_pars_fragment.sh"
#include "../../ShaderChunk/bumpmap_pars_fragment.sh"
#include "../../ShaderChunk/normalmap_pars_fragment.sh"
#include "../../ShaderChunk/roughnessmap_pars_fragment.sh"
#include "../../ShaderChunk/metalnessmap_pars_fragment.sh"
#include "../../ShaderChunk/logdepthbuf_pars_fragment.sh"
#include "../../ShaderChunk/clipping_planes_pars_fragment.sh"

void main() {

	#include "../../ShaderChunk/clipping_planes_fragment.sh"

	vec4 diffuseColor = vec4( diffuse, opacity );
	ReflectedLight reflectedLight = ReflectedLight( vec3( 0.0 ), vec3( 0.0 ), vec3( 0.0 ), vec3( 0.0 ) );
	vec3 totalEmissiveRadiance = emissive;

	#include "../../ShaderChunk/logdepthbuf_fragment.sh"
	#include "../../ShaderChunk/map_fragment.sh"
	#include "../../ShaderChunk/color_fragment.sh"
	#include "../../ShaderChunk/alphamap_fragment.sh"
	#include "../../ShaderChunk/alphatest_fragment.sh"
	#include "../../ShaderChunk/roughnessmap_fragment.sh"
	#include "../../ShaderChunk/metalnessmap_fragment.sh"
	#include "../../ShaderChunk/normal_fragment_begin.sh"
	#include "../../ShaderChunk/normal_fragment_maps.sh"
	#include "../../ShaderChunk/emissivemap_fragment.sh"

	// accumulation
	#include "../../ShaderChunk/lights_physical_fragment.sh"
	#include "../../ShaderChunk/lights_fragment_begin.sh"
	#include "../../ShaderChunk/lights_fragment_maps.sh"
	#include "../../ShaderChunk/lights_fragment_end.sh"

	// modulation
	#include "../../ShaderChunk/aomap_fragment.sh"

	vec3 outgoingLight = reflectedLight.directDiffuse + reflectedLight.indirectDiffuse + reflectedLight.directSpecular + reflectedLight.indirectSpecular + totalEmissiveRadiance;

	gl_FragColor = vec4( outgoingLight, diffuseColor.a );

	#include "../../ShaderChunk/tonemapping_fragment.sh"
	#include "../../ShaderChunk/encodings_fragment.sh"
	#include "../../ShaderChunk/fog_fragment.sh"
	#include "../../ShaderChunk/premultiplied_alpha_fragment.sh"
	#include "../../ShaderChunk/dithering_fragment.sh"

}
