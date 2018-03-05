#define PHYSICAL

varying vec3 vViewPosition;

#ifndef FLAT_SHADED

	varying vec3 vNormal;

#endif

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/uv_pars_vertex.sh"
#include "../../ShaderChunk/uv2_pars_vertex.sh"
#include "../../ShaderChunk/displacementmap_pars_vertex.sh"
#include "../../ShaderChunk/color_pars_vertex.sh"
#include "../../ShaderChunk/fog_pars_vertex.sh"
#include "../../ShaderChunk/morphtarget_pars_vertex.sh"
#include "../../ShaderChunk/skinning_pars_vertex.sh"
#include "../../ShaderChunk/shadowmap_pars_vertex.sh"
#include "../../ShaderChunk/logdepthbuf_pars_vertex.sh"
#include "../../ShaderChunk/clipping_planes_pars_vertex.sh"

void main() {

	#include "../../ShaderChunk/uv_vertex.sh"
	#include "../../ShaderChunk/uv2_vertex.sh"
	#include "../../ShaderChunk/color_vertex.sh"

	#include "../../ShaderChunk/beginnormal_vertex.sh"
	#include "../../ShaderChunk/morphnormal_vertex.sh"
	#include "../../ShaderChunk/skinbase_vertex.sh"
	#include "../../ShaderChunk/skinnormal_vertex.sh"
	#include "../../ShaderChunk/defaultnormal_vertex.sh"

#ifndef FLAT_SHADED // Normal computed with derivatives when FLAT_SHADED

	vNormal = normalize( transformedNormal );

#endif

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/morphtarget_vertex.sh"
	#include "../../ShaderChunk/skinning_vertex.sh"
	#include "../../ShaderChunk/displacementmap_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"
	#include "../../ShaderChunk/logdepthbuf_vertex.sh"
	#include "../../ShaderChunk/clipping_planes_vertex.sh"

	vViewPosition = - mvPosition.xyz;

	#include "../../ShaderChunk/worldpos_vertex.sh"
	#include "../../ShaderChunk/shadowmap_vertex.sh"
	#include "../../ShaderChunk/fog_vertex.sh"

}
