uniform vec3 color;
uniform float opacity;

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/packing.sh"
#include "../../ShaderChunk/fog_pars_fragment.sh"
#include "../../ShaderChunk/bsdfs.sh"
#include "../../ShaderChunk/lights_pars_begin.sh"
#include "../../ShaderChunk/shadowmap_pars_fragment.sh"
#include "../../ShaderChunk/shadowmask_pars_fragment.sh"

void main() {

	gl_FragColor = vec4( color, opacity * ( 1.0 - getShadowMask() ) );

	#include "../../ShaderChunk/fog_fragment.sh"

}
