package com.ihm.app.graphics;

import java.util.ArrayList;

import com.ihm.maths.MathUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

public class CursorTestView extends View{

	// Outil de dessin
	protected Paint _paint = new Paint();
	
	// Position du reticule
	protected PointF _reticuleLocation = new PointF();
	
	// Taille du réticule
	protected float _reticuleSize = 50;
	
	// Rectangle buffer
	protected RectF _buffRect = new RectF();
	
	// Position du curseur
	PointF _cursor;
	
	// Nombre de ligne
	int _nbLines = 15; 
	
	// Nombre minimum de colonnes
	int _minCols = 5;
	
	// Taille min et max d'une colonne
	float _minColSize = 15, _maxColSize = 30;
	
	// Hauteur d'une ligne
	float _heightLine = 35;
	
	// Espace inter-ligne
	float _spacing = 12;
	
	// Espace autour de la grille
	float _offset = 15;
	
	int _gridFractal = 10;
	
	PointF _cursorLocation = new PointF();
	
	boolean _showReticule = true;
	
	// Grille générée
	// Pair A: Taille de la colonne en X
	// Pair B; Taille cummulée
	ArrayList <ArrayList<Pair<Float, Float>>> _grid;
	
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
		
		if(_grid == null)
			buildGrid();
		
		
		
		// Recherche du Curseur
		
			// Recherche de la ligne
			int cursorLine = (int)((_reticuleLocation.y - _offset + _spacing/2) / (_heightLine + _spacing));
			cursorLine = Math.max(cursorLine, 0);
			cursorLine = Math.min(cursorLine, _grid.size() - 1);
			_cursorLocation.y =  _offset + (_heightLine + _spacing) * cursorLine;

			// Recherche de la colonne
			float reticuleCurseurDistance = getWidth();
			for (Pair<Float, Float> col : _grid.get(cursorLine)) {

				// Calcul de la distance entre le réticule et le curseur
				float newReticuleCurseurDistance = Math.abs(_reticuleLocation.x - (col.second + _offset));
				if(reticuleCurseurDistance > newReticuleCurseurDistance ){
					reticuleCurseurDistance = newReticuleCurseurDistance;
					_cursorLocation.x = col.second + _offset;
				}
			}
			
			// Permet de déplacer le curseur tout à gauche
			if(reticuleCurseurDistance > _offset && _reticuleLocation.x < _offset + _grid.get(cursorLine).get(0).first / 2)
				_cursorLocation.x =  _offset;
			
			
		// Affichage : Curseur
		_paint.setStrokeWidth(3);
		_paint.setColor(Color.BLUE);
		canvas.drawLine(_cursorLocation.x, _cursorLocation.y, _cursorLocation.x, _cursorLocation.y + _heightLine, _paint);
		
		// Affichage : Grille
			
			// Position actuelle de la ligne
			Float linePos = _offset;
			
			// Parcours des lignes
			for (ArrayList<Pair<Float, Float>> line : _grid) {
				
				// Paint grille
				_paint.setStrokeWidth(1);
				_paint.setColor(Color.GRAY);
				
				// Affichage des deux lignes horyzontale
				canvas.drawLine(_offset, linePos, line.get(line.size()-1).second + _offset, linePos, _paint);
				canvas.drawLine(_offset, linePos + _heightLine, line.get(line.size()-1).second + _offset, linePos + _heightLine, _paint);
				
				// Premiere ligne verticale de la ligne
				canvas.drawLine(_offset, linePos, _offset, linePos + _heightLine, _paint);
				
				for (Pair<Float, Float> col : line)
					canvas.drawLine(_offset + col.second, linePos,_offset + col.second, linePos + _heightLine, _paint);
				
				// Aujout d'une ligne
				linePos += _heightLine + _spacing;
			}
			
		// Reticule
		if(_showReticule){
			_paint.setColor(Color.RED);
			_paint.setStrokeWidth(2);
			float reticuleDiff = _reticuleSize/2.0F;
			canvas.drawLine(_reticuleLocation.x - reticuleDiff, _reticuleLocation.y, _reticuleLocation.x + reticuleDiff, _reticuleLocation.y, _paint);
			canvas.drawLine(_reticuleLocation.x, _reticuleLocation.y - reticuleDiff, _reticuleLocation.x, _reticuleLocation.y + reticuleDiff, _paint);
		}
	}
	
	public void translateReticule(PointF vector){
		PointF reticule = new PointF(_reticuleLocation.x + vector.x, _reticuleLocation.y + vector.y);
		
		// Borne X
		reticule.x = Math.max(reticule.x, 0);
		reticule.x = Math.min(reticule.x, getWidth());
		
		// Borne Y
		reticule.y = Math.max(reticule.y, 0);
		reticule.y = Math.min(reticule.y, getHeight());
		
		_reticuleLocation.set(reticule);
	}
	
	public void buildGrid(){
		// Création de la grille
		_grid = new ArrayList<ArrayList<Pair<Float, Float>>>(_nbLines);
		
		// Génération des lignes
		for(int i = 0; i<_nbLines; i++){
			ArrayList<Pair<Float, Float>> cols = new ArrayList<Pair<Float, Float>>();
			
			float comboSize = 0;
			for(int j = 0; comboSize + _maxColSize < getWidth() - (_offset * 2) ; j++){
				
				// Cas où le nombre de case min est atteind
				if(cols.size() > _minCols){
					if(MathUtils.random(0, _gridFractal) == 0)
						break;
				}
				
				// Génération de la taille de la colonne
				float colSize = (float)MathUtils.random(_minColSize, _maxColSize);
				comboSize += colSize;
				cols.add(j, new Pair<Float, Float>(colSize, comboSize));
			}
			
			// Ajout des colonnes à la ligne
			_grid.add(i, cols);
		}
	}
	public PointF getReticuleLocation() {
		return _reticuleLocation;
	}
	public void setReticuleLocation(PointF reticuleLocation) {
		this._reticuleLocation = reticuleLocation;
	}
	public PointF getCursorLocation() {
		return _cursorLocation;
	}
	public void setCursorLocation(PointF cursorLocation) {
		this._cursorLocation = cursorLocation;
	}
	
	public void autoplaceResticule(){
		_reticuleLocation.x = _cursorLocation.x;
		_reticuleLocation.y = _cursorLocation.y + _heightLine / 2;
	}
		
	public void setShowReticule(boolean show){
		_showReticule = show;
	}
	
	public boolean isShowReticule(){
		return _showReticule;
	}
	public void setNbLines(int nbLines) {
		this._nbLines = nbLines;
	}
	public void setNinCols(int minCols) {
		this._minCols = minCols;
	}
	public void setMinColSize(float minColSize) {
		this._minColSize = minColSize;
	}
	public void setMaxColSize(float maxColSize) {
		this._maxColSize = maxColSize;
	}
	public void setHeightLine(float heightLine) {
		this._heightLine = heightLine;
	}
	public void setSpacing(float spacing) {
		this._spacing = spacing;
	}
	public void setOffset(float offset) {
		this._offset = offset;
	}
	public void setReticuleSize(float reticuleSize) {
		this._reticuleSize = reticuleSize;
	}
	public void setMinCols(int minCols) {
		this._minCols = minCols;
	}
	public void setGridFractal(int gridFractal) {
		this._gridFractal = gridFractal;
	}
	public float getOffset() {
		return _offset;
	}
	public PointF get_cursorLocation() {
		return _cursorLocation;
	}
	
	
}
