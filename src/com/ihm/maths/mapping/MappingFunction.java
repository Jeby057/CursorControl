package com.ihm.maths.mapping;

import android.graphics.PointF;

public abstract class MappingFunction {

	float _sensibility = 1.0F;
	
	public void setSensibility(float sensibility){
		_sensibility = sensibility;
	}
	
	public float execute(float value){
		return run(value * _sensibility);
	}
	
	protected abstract float run(float value);
	
	public PointF execute(PointF point){
		point.set(execute(point.x), execute(point.y));
		return point;
	}
}
