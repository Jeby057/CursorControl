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
	float _circleOutRadius = 50;
	
	// Rayon du cercle intérieur
	float _circleInRadius = 10;
	
	// Position du cercle
	PointF _circleLocation = new PointF();
	
	// Position du pointeur
	PointF _pointerLocation = new PointF();
	
	// Boolean permettant d'afficher ou de ne pas afficher le cercle
	boolean _print = false;
	
	// Décalage X, Y du cercle
	PointF _offset = new PointF();
	
	// Tableaux d'information
	String[] _infos = new String[]{};
	
	float _infoTextSize = 24;
	
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
	public void setCircleOutRadius(float radius){
		_circleOutRadius = radius;
	}
	
	/**
	 * Modifie le rayon intérieur du cercle
	 * @param radius Nouveau rayon
	 */
	public void setCircleInRadius(float radius){
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
	
	public void setTextInfoSize(float infoTextSize){
		_infoTextSize = infoTextSize;
	}

	
	/**
	 * Modifie l'offset permettant de décaler en x et y le cercle
	 * @param offset Décalage X, Y
	 */
	public void setOffset(PointF offset) {
		this._offset = offset;
	}

	@Override
	public void onDraw(Canvas canvas) {
		
		// Affichage des informations
		_paint.setColor(Color.BLACK);
        _paint.setTextSize(_infoTextSize);
        for (int i = 0; i < _infos.length; i++) {
            canvas.drawText(_infos[i], 0, _infoTextSize * (i + 1), _paint);
		}
	        
        // On ne dessine uniquement les informations relatives au pointage
        // si print vaut vrai
		if(!_print)
			return;
		
		// Cercle exterieur
		_paint.setColor(Color.RED);
        canvas.drawCircle(_circleLocation.x + _offset.x, _circleLocation.y + _offset.y, _circleOutRadius, _paint);
        
        // Cercle intérieur
        _paint.setColor(Color.WHITE);
        canvas.drawCircle(_circleLocation.x + _offset.x, _circleLocation.y + _offset.y, _circleInRadius, _paint);
        
        // Pointeur
		_paint.setColor(Color.BLUE);
        canvas.drawLine(_pointerLocation.x + _offset.x,  0, _pointerLocation.x + _offset.x, getHeight(), _paint);
        canvas.drawLine(0, _pointerLocation.y + _offset.y, getWidth(), _pointerLocation.y + _offset.y,  _paint);       
	}
	
	
}
