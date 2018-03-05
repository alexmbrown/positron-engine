uniform vec3 diffuse;
uniform vec3 emissive;
uniform float opacity;

varying vec3 vLightFront;

#ifdef DOUBLE_SIDED

	varying vec3 vLightBack;

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
#include "../../ShaderChunk/bsdfs.sh"
#include "../../ShaderChunk/lights_pars_begin.sh"
#include "../../ShaderChunk/lights_pars_maps.sh"
#include "../../ShaderChunk/fog_pars_fragment.sh"
#include "../../ShaderChunk/shadowmap_pars_fragment.sh"
#include "../../ShaderChunk/shadowmask_pars_fragment.sh"
#include "../../ShaderChunk/specularmap_pars_fragment.sh"
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
	#include "../../ShaderChunk/specularmap_fragment.sh"
	#include "../../ShaderChunk/emissivemap_fragment.sh"

	// accumulation
	reflectedLight.indirectDiffuse = getAmbientLightIrradiance( ambientLightColor );

	#include "../../ShaderChunk/lightmap_fragment.sh"

	reflectedLight.indirectDiffuse *= BRDF_Diffuse_Lambert( diffuseColor.rgb );

	#ifdef DOUBLE_SIDED

		reflectedLight.directDiffuse = ( gl_FrontFacing ) ? vLightFront : vLightBack;

	#else

		reflectedLight.directDiffuse = vLightFront;

	#endif

	reflectedLight.directDiffuse *= BRDF_Diffuse_Lambert( diffuseColor.rgb ) * getShadowMask();

	// modulation
	#include "../../ShaderChunk/aomap_fragment.sh"

	vec3 outgoingLight = reflectedLight.directDiffuse + reflectedLight.indirectDiffuse + totalEmissiveRadiance;

	#include "../../ShaderChunk/envmap_fragment.sh"

	gl_FragColor = vec4( outgoingLight, diffuseColor.a );

	#include "../../ShaderChunk/tonemapping_fragment.sh"
	#include "../../ShaderChunk/encodings_fragment.sh"
	#include "../../ShaderChunk/fog_fragment.sh"
	#include "../../ShaderChunk/premultiplied_alpha_fragment.sh"
	#include "../../ShaderChunk/dithering_fragment.sh"

}
