uniform float size;
uniform float scale;

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/color_pars_vertex.sh"
#include "../../ShaderChunk/fog_pars_vertex.sh"
#include "../../ShaderChunk/shadowmap_pars_vertex.sh"
#include "../../ShaderChunk/logdepthbuf_pars_vertex.sh"
#include "../../ShaderChunk/clipping_planes_pars_vertex.sh"

void main() {

	#include "../../ShaderChunk/color_vertex.sh"
	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"

	#ifdef USE_SIZEATTENUATION
		gl_PointSize = size * ( scale / - mvPosition.z );
	#else
		gl_PointSize = size;
	#endif

	#include "../../ShaderChunk/logdepthbuf_vertex.sh"
	#include "../../ShaderChunk/clipping_planes_vertex.sh"
	#include "../../ShaderChunk/worldpos_vertex.sh"
	#include "../../ShaderChunk/shadowmap_vertex.sh"
	#include "../../ShaderChunk/fog_vertex.sh"

}
