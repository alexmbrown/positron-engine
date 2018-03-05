varying vec3 vWorldPosition;

#include "../../ShaderChunk/common.sh"

void main() {

	vWorldPosition = transformDirection( position, modelMatrix );

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"

}
