uniform float scale;
attribute float lineDistance;

varying float vLineDistance;

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/color_pars_vertex.sh"
#include "../../ShaderChunk/fog_pars_vertex.sh"
#include "../../ShaderChunk/logdepthbuf_pars_vertex.sh"
#include "../../ShaderChunk/clipping_planes_pars_vertex.sh"

void main() {

	#include "../../ShaderChunk/color_vertex.sh"

	vLineDistance = scale * lineDistance;

	vec4 mvPosition = modelViewMatrix * vec4( position, 1.0 );
	gl_Position = projectionMatrix * mvPosition;

	#include "../../ShaderChunk/logdepthbuf_vertex.sh"
	#include "../../ShaderChunk/clipping_planes_vertex.sh"
	#include "../../ShaderChunk/fog_vertex.sh"

}
