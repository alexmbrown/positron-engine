#ifdef USE_MORPHTARGETS

	transformed += ( morphTarget0 - a_position ) * morphTargetInfluences[ 0 ];
	transformed += ( morphTarget1 - a_position ) * morphTargetInfluences[ 1 ];
	transformed += ( morphTarget2 - a_position ) * morphTargetInfluences[ 2 ];
	transformed += ( morphTarget3 - a_position ) * morphTargetInfluences[ 3 ];

	#ifndef USE_MORPHNORMALS

	transformed += ( morphTarget4 - a_position ) * morphTargetInfluences[ 4 ];
	transformed += ( morphTarget5 - a_position ) * morphTargetInfluences[ 5 ];
	transformed += ( morphTarget6 - a_position ) * morphTargetInfluences[ 6 ];
	transformed += ( morphTarget7 - a_position ) * morphTargetInfluences[ 7 ];

	#endif

#endif
