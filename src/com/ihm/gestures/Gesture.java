package com.ihm.gestures;


import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Gesture implements OnTouchListener{

	// Dernière position detectée
	protected PointF _firstPosition = new PointF(0, 0);
	
	// Position actuelle
	protected PointF _position = new PointF(0, 0);

	protected OnGestureListener _listener;
	
	public Gesture(OnGestureListener listener) {
		super();
		_listener = listener;
	}

	protected void raiseListener(){
		_listener.onGesture();
	}
	
	public PointF getFirstPosition(){
		return _firstPosition;
	}
	
	public PointF getPosition(){
		return _position;
	}
	
	@Override
	public boolean onTouch(View arg0, MotionEvent e){
		_position.x = e.getX();
		_position.y = e.getY();
 
		// Mise à jour de l'ancienne position à lors de l'appuis
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			_firstPosition.set(e.getX(), e.getY());
			_listener.onStartGesture();
			return true;
		}
		else if (e.getAction() == MotionEvent.ACTION_UP) {
			_listener.onStopGesture();
			return false;
		}
		
		return false;
	}

}
