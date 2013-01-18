package com.ihm.renders.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.ihm.renders.RenderView;

public class SimpleRenderView extends RenderView{

	
		
	public SimpleRenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SimpleRenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SimpleRenderView(Context context) {
		super(context);
	}
	
	@Override
	public void invalidate() {
		_buffRect.set(_reticuleLocation.x - _reticuleSize, _reticuleLocation.y - _reticuleSize, _reticuleLocation.x + _reticuleSize, _reticuleLocation.y + _reticuleSize);	
		super.invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		_paint.setColor(Color.BLUE);
        canvas.drawRect(_buffRect, _paint);
	}

}
