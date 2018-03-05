#define DISTANCE

varying vec3 vWorldPosition;

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/uv_pars_vertex.sh"
#include "../../ShaderChunk/displacementmap_pars_vertex.sh"
#include "../../ShaderChunk/morphtarget_pars_vertex.sh"
#include "../../ShaderChunk/skinning_pars_vertex.sh"
#include "../../ShaderChunk/clipping_planes_pars_vertex.sh"

void main() {

	#include "../../ShaderChunk/uv_vertex.sh"

	#include "../../ShaderChunk/skinbase_vertex.sh"

	#ifdef USE_DISPLACEMENTMAP

		#include "../../ShaderChunk/beginnormal_vertex.sh"
		#include "../../ShaderChunk/morphnormal_vertex.sh"
		#include "../../ShaderChunk/skinnormal_vertex.sh"

	#endif

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/morphtarget_vertex.sh"
	#include "../../ShaderChunk/skinning_vertex.sh"
	#include "../../ShaderChunk/displacementmap_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"
	#include "../../ShaderChunk/worldpos_vertex.sh"
	#include "../../ShaderChunk/clipping_planes_vertex.sh"

	vWorldPosition = worldPosition.xyz;

}
