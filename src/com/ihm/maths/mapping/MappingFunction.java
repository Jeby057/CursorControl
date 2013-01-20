package com.ihm.maths.mapping;

import android.graphics.PointF;

public abstract class MappingFunction {

	float _sensibility = 1.0F;
	
	public void setSensibility(float sensibility){
		_sensibility = sensibility;
	}
	
	public float execute(float value){
		return run(Math.abs(value) * _sensibility) * ( (value==0) ? 0:(Math.abs(value)/value));
	}
	
	public PointF execute(PointF point){
		point = new PointF(execute(point.x), execute(point.y));
		return point;
	}
	
	protected abstract float run(float value);
}
