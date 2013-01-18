package com.ihm.app.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class MicroRollGestureView extends View{

	// Outil de dessin
	Paint _paint = new Paint();
	
	// Rayon du cercle extérieur
	int _circleOutRadius = 50;
	
	// Rayon du cercle intérieur
	int _circleInRadius = 10;
	
	// Position du cercle
	PointF _circleLocation = new PointF();
	
	// Position du pointeur
	PointF _pointerLocation = new PointF();
	
	// Boolean permettant d'afficher ou de ne pas afficher le cercle
	boolean _print = false;
	
	// Tableaux d'information
	String[] _infos = new String[]{};
	
	public MicroRollGestureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MicroRollGestureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MicroRollGestureView(Context context) {
		super(context);
	}
	
	/**
	 * Modifie le rayon exterieur du cercle
	 * @param radius Nouveau rayon
	 */
	public void setCircleOutRadius(int radius){
		_circleOutRadius = radius;
	}
	
	/**
	 * Modifie le rayon intérieur du cercle
	 * @param radius Nouveau rayon
	 */
	public void setCircleInRadius(int radius){
		_circleInRadius = radius;
	}
	
	/**
	 * Modifie la position du cercle
	 * @param radius Nouvelle position
	 */
	public void setCircleLocation(PointF location){
		_circleLocation = location;
	}
	
	/**
	 * Modifie la position du pointeur
	 * @param location Nouvelle position
	 */
	public void setPointerLocation(PointF location){
		_pointerLocation = location;
	}
	
	/**
	 * Modifie le tableau d'information
	 * @param infos Nouveau tableau de chaines
	 */
	public void setInfos(String[] infos){
		_infos = infos;
	}
	
	/**
	 * Modifie si les informations de pointages doivent être affichées
	 * @param print Vrai pour les afficher, faux sinon
	 */
	public void setPrint(boolean print){
		_print = print;
	}


	@Override
	public void onDraw(Canvas canvas) {
		
		// Affichage des informations
		_paint.setColor(Color.BLACK);
        _paint.setTextSize(24);
        for (int i = 0; i < _infos.length; i++) {
            canvas.drawText(_infos[i], 0, 24 * (i + 1), _paint);
		}
	        
        // On ne dessine uniquement les informations relatives au pointage
        // si print vaut vrai
		if(!_print)
			return;
		
		// Cercle exterieur
		_paint.setColor(Color.RED);
        canvas.drawCircle(_circleLocation.x, _circleLocation.y, _circleOutRadius, _paint);
        
        // Cercle intérieur
        _paint.setColor(Color.WHITE);
        canvas.drawCircle(_circleLocation.x, _circleLocation.y, _circleInRadius, _paint);
        
        // Pointeur
		_paint.setColor(Color.BLUE);
        canvas.drawLine(_pointerLocation.x, 0, _pointerLocation.x, getHeight(), _paint);
        canvas.drawLine(0, _pointerLocation.y, getWidth(), _pointerLocation.y,  _paint);       
	}
	
	
}
