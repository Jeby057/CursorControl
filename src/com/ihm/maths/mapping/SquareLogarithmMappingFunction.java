package com.ihm.maths.mapping;

import com.ihm.maths.MathUtils;

public class SquareLogarithmMappingFunction extends MappingFunction{

	@Override
	protected float run(float value) {
		return (float) (MathUtils.square(value) * Math.log(value));
	}

}
