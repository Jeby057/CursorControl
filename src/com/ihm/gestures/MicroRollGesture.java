package com.ihm.gestures;

import com.ihm.maths.MathUtils;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class MicroRollGesture extends Gesture{

	public MicroRollGesture(OnGestureListener listener) {
		super(listener);
	}

	// Dernier Roll effectu�
	PointF _roll = new PointF();
	
	// Port�e de la zone (Rayon)
	float _scope = 50;
	
	// Taille de l'espace neutre
	float _nullAreaSize = 10;
	
	// Autorise la translation de la zone si l'utilisateur 
	// sort de celle-ci
	boolean _allowTranslateArea = true;
	
	public PointF getMicroRoll(){
		return _roll;
	}
	
	public void setScope(float scope){
		_scope = scope;
	}
	
	public void setNullAreaSize(float size){
		_nullAreaSize = size;
	}
	
	@Override
	public boolean onTouch(View arg, MotionEvent e) {
		if(super.onTouch(arg, e)){
			raiseListener();
			return true;
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			_roll.set(0, 0);
			return false;
		}
		
		// Nouveau roll
		PointF newRoll = new PointF(e.getX() - _firstPosition.x, e.getY() - _firstPosition.y);

		// V�rification de la port� du micro roll 
		if (!MathUtils.isInCircle(_firstPosition, new PointF(e.getX(), e.getY()), _scope)) {
			
			// Si le geste en mode hors de port� est autorit�
			// On translate la zone de roll
			if(_allowTranslateArea){
				
				// Calcul de la nouvelle position de la zone de Micro Roll
				double hypoRectRoll = Math.sqrt(MathUtils.square(newRoll.x) + MathUtils.square(newRoll.y));
				_firstPosition.x += newRoll.x - _scope / hypoRectRoll * newRoll.x;
				_firstPosition.y += newRoll.y - _scope / hypoRectRoll * newRoll.y;

			}
			else
				return false;
		} 

		// V�rification de la port� de la zone null
		else if(MathUtils.isInCircle(_firstPosition, new PointF(e.getX(), e.getY()), _nullAreaSize))
			_roll.set(0,0);
		
		// Mise � jour du vecteur de roll
		else
			_roll.set(newRoll.x, newRoll.y);
		
		// Appel du listener
		raiseListener();
		
		return (e.getAction() == MotionEvent.ACTION_MOVE);
	}
	
}
