$input a_position, a_normal
$output v_color0, v_normal

//#include "./bgfx/common.sh"

uniform vec4 u_color;

void main()
{
    gl_Position = mul(u_modelViewProj, vec4(a_position, 1.0) );
    v_normal = a_normal
    v_color0 = u_color;
}