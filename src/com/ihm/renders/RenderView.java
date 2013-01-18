package com.ihm.renders;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RenderView extends View{

	// Outil de dessin
	protected Paint _paint = new Paint();
	
	// Position du reticule
	protected PointF _reticuleLocation = new PointF();
	
	// Zone de gesture (Rayon et position)
	protected int _reticuleSize = 50;
	
	// Rectangle buffer
	protected RectF _buffRect = new RectF();
	

	protected ArrayList<PointF> _trace = new ArrayList<PointF>();
	

	protected boolean _resetReticule = false;
	
	public void resetResticule()
	{
		_resetReticule = true;
	}
	
	public void translateReticule(PointF location){
		_reticuleLocation.x += location.x;
		_reticuleLocation.y += location.y;
		
		// Borne X
		_reticuleLocation.x = Math.max(_reticuleLocation.x, 0);
		_reticuleLocation.x = Math.min(_reticuleLocation.x, getWidth());
		
		// Borne Y
		_reticuleLocation.y = Math.max(_reticuleLocation.y, 0);
		_reticuleLocation.y = Math.min(_reticuleLocation.y, getHeight());
		
		//_trace.add(_reticuleLocation);
	}
	
	public RenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RenderView(Context context) {
		super(context);
	}
	

}
