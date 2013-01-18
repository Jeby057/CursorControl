package com.ihm.app.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CursorTestView extends View{

	// Outil de dessin
	protected Paint _paint = new Paint();
	
	// Position du reticule
	protected PointF _reticuleLocation = new PointF();
	
	// Taille du réticule
	protected int _reticuleSize = 50;
	
	// Rectangle buffer
	protected RectF _buffRect = new RectF();
	
	public CursorTestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public CursorTestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CursorTestView(Context context) {
		super(context);
	}

	@Override
	public void onDraw(Canvas canvas) {


		
	}
		
}
