package com.ihm.renders.grid;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.ihm.app.graphics.GridRenderData;
import com.ihm.renders.RenderView;

public class GridRenderView extends RenderView{

	GridRenderData _data;
	
	
	public GridRenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GridRenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridRenderView(Context context) {
		super(context);
	}
	
	public void setData(GridRenderData data){
		_data = data;
	}
	
	
	@Override
	public void invalidate() {
		super.invalidate();
	}


	@Override
	public void onDraw(Canvas canvas) {
		if(_data == null || _data.getGrid() == null)
			return;
		
		_paint.setColor(Color.GRAY);
		
		// Curseur
		// Recherche de la ligne
		int cursorLine = (int) ((_reticuleLocation.y - _data.getOffset() + _data.getIterLine()/2) / (_data.getHeightLine() + _data.getIterLine()));
		cursorLine = Math.max(cursorLine, 0);
		cursorLine = Math.min(cursorLine, _data.getGrid().size() - 1);
		
		// Grille
		int line = 0;
		float startY = (float)_data.getOffset();
		
		for (ArrayList<Float> lineBox : _data.getGrid()) {

			float startX = (float)_data.getOffset();
			float cursorColPixDiff = getWidth();
			float cursorColPix = getWidth();
			
			// Premiere bordure verticale
			_paint.setStrokeWidth(1);
			_paint.setColor(Color.GRAY);
			canvas.drawLine(startX, startY, startX, startY + (float)_data.getHeightLine(), _paint);
			
			// Autres bordures
			for (Float val : lineBox) {
				startX += val;
				
				canvas.drawLine(startX, startY, startX, startY + (float)_data.getHeightLine(), _paint);
				
				// Gestion du curseur
				if(line == cursorLine){
					float newCursorColPixDiff = Math.abs(_reticuleLocation.x - startX);
					if(cursorColPixDiff > newCursorColPixDiff)
					{
						cursorColPixDiff = newCursorColPixDiff;
						cursorColPix = startX;
					}
				}
			}
			
			// Lignes
			canvas.drawLine((float)_data.getOffset(), startY, startX, startY, _paint);
			canvas.drawLine((float)_data.getOffset(), startY + (float)_data.getHeightLine(), startX, startY + (float)_data.getHeightLine(), _paint);
			
			// Affichage du curseur
			if(line == cursorLine)
			{
				_paint.setStrokeWidth(3);
				_paint.setColor(Color.BLUE);
				canvas.drawLine(cursorColPix, startY, cursorColPix, startY + (float)_data.getHeightLine(), _paint);
			
				if(_resetReticule){
					_resetReticule = false;
					_reticuleLocation.x = cursorColPix;
					_reticuleLocation.y = startY + (float)_data.getHeightLine()/2;
				}
			}
						
			// On passe à la ligne suivante
			startY += _data.getHeightLine() + _data.getIterLine();
			line++;
		}
		
		// Trace
		//_paint.setColor(Color.RED);
		//for (int i=0; i<_trace.size()-2; i++) {
		//	canvas.drawLine(_trace.get(i).x, _trace.get(i).y, _trace.get(i+1).x, _trace.get(i+1).y, _paint);
		//}
		
		// Reticule
		
		_paint.setColor(Color.RED);
		_paint.setStrokeWidth(2);
		float reticuleDiff = _reticuleSize/2.0F;
		canvas.drawLine(_reticuleLocation.x - reticuleDiff, _reticuleLocation.y, _reticuleLocation.x + reticuleDiff, _reticuleLocation.y, _paint);
		canvas.drawLine(_reticuleLocation.x, _reticuleLocation.y - reticuleDiff, _reticuleLocation.x, _reticuleLocation.y + reticuleDiff, _paint);
		
		
		
	}

}
