package com.ihm.maths.mapping;


public class CustomExponentialMappingFunction extends MappingFunction{

	@Override
	protected float run(float value) {
		// e^((x-5)/2)
		return (float) (Math.exp((value-5)/2));
	}

}
