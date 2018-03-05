vec4 mvPosition = u_modelView * vec4( transformed, 1.0 );

gl_Position = u_proj * mvPosition;
