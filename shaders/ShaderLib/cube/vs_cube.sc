$input a_position
$output v_worldPosition

#include "../../ShaderChunk/common.sh"

void main() {

	v_worldPosition = transformDirection( a_position, u_model[0] );

	#include "../../ShaderChunk/begin_vertex.sh"
	#include "../../ShaderChunk/project_vertex.sh"

	gl_Position.z = gl_Position.w; // set z to camera.far

}
