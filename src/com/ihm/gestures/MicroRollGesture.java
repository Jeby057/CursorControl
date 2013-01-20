package com.ihm.gestures;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import com.ihm.maths.MathUtils;

public class MicroRollGesture extends Gesture{

	public MicroRollGesture(OnGestureListener listener) {
		super(listener);
	}

	// Dernier Roll effectué
	float _roll = 0.0F;
	
	PointF _direction = new PointF();
	
	// Portée de la zone (Rayon)
	float _scope = 50;
	
	// Taille de l'espace neutre
	float _nullAreaRadius = 15;
	
	// Autorise la translation de la zone si l'utilisateur 
	// sort de celle-ci
	boolean _allowTranslateArea = true;
	
	boolean _isInNullArea = false;
	
	public float getMicroRollValue(){
		return _roll;
	}
	
	public PointF getMicroRollDirection(){
		return _direction;
	}
	
	public void setScope(float scope){
		_scope = scope;
	}
	
	public void setNullAreaSize(float size){
		_nullAreaRadius = size;
	}
	
	@Override
	public boolean onTouch(View arg, MotionEvent e) {
		
		
		if(super.onTouch(arg, e)){
			raiseListener();
			return true;
		}
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			_isInNullArea = true;
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			_roll = 0.0F;
			return false;
		}
		
		// Nouveau roll
		PointF v = new PointF(e.getX() - _firstPosition.x, e.getY() - _firstPosition.y);
		
		_roll = MathUtils.norme(v) /*- _nullAreaRadius*/;
		_direction = new PointF(v.x/MathUtils.norme(v), v.y/MathUtils.norme(v));
		
		// Vérification de la porté du micro roll 
		if (_roll > _scope) {
			
			// Si le geste en mode hors de porté est autorité
			// On translate la zone de roll
			if(_allowTranslateArea){
				
				// Calcul de la nouvelle position de la zone de Micro Roll
				_firstPosition.x = e.getX() - _direction.x * _scope;
				_firstPosition.y = e.getY() - _direction.y * _scope;
			}
			else
				return false;
		}

		// Vérification de la porté de la zone null
		if(_roll < _nullAreaRadius){
			_roll = 0.0F;
			
			// Recentre le virtual pad sur le doigt quand on rentre dans la zone null
			if(!_isInNullArea){
				_firstPosition.set(e.getX(), e.getY());
				_isInNullArea = true;
			}
		}
		
		// Mise à jour du vecteur de roll
		else{ 
			// On soustrait la zone neutre
			_roll -= _nullAreaRadius;
			
			_isInNullArea = false;
		}
		
		// Appel du listener
		raiseListener();
		
		return (e.getAction() == MotionEvent.ACTION_MOVE);
	}
	
}
