package com.ihm.maths;

import android.graphics.PointF;

public class MathUtils {

	public static float square(float value){
		return value * value;
	}

	public static boolean isInCircle(PointF center, PointF point, float radius){
		return(MathUtils.square(point.x - center.x) + MathUtils.square(point.y - center.y) < MathUtils.square(radius));
	}
	
	public static PointF factor(PointF vect1, float value){
		return new PointF(vect1.x * value, vect1.y * value);
	}
	
	public static PointF factor(PointF vect1, PointF vect2){
		return new PointF(vect1.x * vect2.x, vect1.y * vect2.y);
	}
	
	public static double random(double min, double max){
		return min + (Math.random() * (max - min)) ;
	}
	
	public static int random(int min, int max){
		return min + (int)(Math.random() * ((max) + 1));
	}

	public static float radToDeg(float value){
		return (value*180)/(float)Math.PI;
	}
	
	public static float norme(PointF vector){
		return (float)Math.sqrt(square(vector.x) + square(vector.y));
	}
	
	public static float distance(PointF p1, PointF p2){
		return  (float)Math.sqrt(square(p2.x - p1.x) + square(p2.y - p1.y));
	}
}
