package com.ihm.maths.pseudohaptic;

import android.graphics.PointF;

import com.ihm.maths.MathUtils;

public class PseudoHapticProjection {

	float _angle;
	
	public PseudoHapticProjection(float angle) {
		_angle = angle;
	}
	
	public PointF projection(PointF v){
		
		// Mise en valeurs absolues
		PointF vector = new PointF(Math.abs(v.x), Math.abs(v.y));
		
		// Déplacement horizontal
		if(vector.x > vector.y && v.x != 0){
			if(isInGap(vector.y, vector.x)){
				return new PointF(v.x, 0);
			}
		}
		
		// Déplacement vertical
		else if(vector.x < vector.y && v.y != 0){
			if(isInGap(vector.x, vector.y)){
				return new PointF(0, v.y);
			}
		}
		return v;
	}

	private boolean isInGap(float opp, float adj){
		return (MathUtils.radToDeg((float) Math.atan(opp / adj)) < _angle);
	}
	
}
