$input v_color0, v_normal

/*
 * Copyright 2011-2018 Branimir Karadzic. All rights reserved.
 * License: https://github.com/bkaradzic/bgfx#license-bsd-2-clause
 */

//#include "./bgfx/common.sh"

uniform vec4 u_color;

void main()
{
	gl_FragColor = v_color0;
}