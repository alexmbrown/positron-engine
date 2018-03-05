#include "../../ShaderChunk/fog_pars_vertex.sh"
#include "../../ShaderChunk/shadowmap_pars_vertex.sh"

void main() {

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"
	#include "../../ShaderChunk/worldpos_vertex.sh"
	#include "../../ShaderChunk/shadowmap_vertex.sh"
	#include "../../ShaderChunk/fog_vertex.sh"

}
