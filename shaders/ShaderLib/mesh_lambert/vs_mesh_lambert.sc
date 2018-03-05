#define LAMBERT

varying vec3 vLightFront;

#ifdef DOUBLE_SIDED

	varying vec3 vLightBack;

#endif

#include "../../ShaderChunk/common.sh"
#include "../../ShaderChunk/uv_pars_vertex.sh"
#include "../../ShaderChunk/uv2_pars_vertex.sh"
#include "../../ShaderChunk/envmap_pars_vertex.sh"
#include "../../ShaderChunk/bsdfs.sh"
#include "../../ShaderChunk/lights_pars_begin.sh"
#include "../../ShaderChunk/lights_pars_maps.sh"
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

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/morphtarget_vertex.sh"
	#include "../../ShaderChunk/skinning_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"
	#include "../../ShaderChunk/logdepthbuf_vertex.sh"
	#include "../../ShaderChunk/clipping_planes_vertex.sh"

	#include "../../ShaderChunk/worldpos_vertex.sh"
	#include "../../ShaderChunk/envmap_vertex.sh"
	#include "../../ShaderChunk/lights_lambert_vertex.sh"
	#include "../../ShaderChunk/shadowmap_vertex.sh"
	#include "../../ShaderChunk/fog_vertex.sh"

}
