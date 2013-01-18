package com.ihm.renders.grid;

import java.util.ArrayList;

import android.graphics.PointF;

import com.ihm.maths.MathUtils;

public class GridRenderData {

	ArrayList <ArrayList<Float>> _grid;
	
	int _nbLines; 
	int _minCols; 
	int _maxCols; 
	double _minSize; 
	double _maxSize;
	double _heightLine;
	double _iterLine;
	double _offset;
	PointF _cursor;
	
	
	
	public GridRenderData(int nbLines, double heightLine, double iterLine, int minCols, int maxCols,
			double minSize, double maxSize, double offset) {
		super();
		this._nbLines = nbLines;
		this._minCols = minCols;
		this._maxCols = maxCols;
		this._minSize = minSize;
		this._maxSize = maxSize;
		this._heightLine = heightLine;
		this._iterLine = iterLine;
		this._offset = offset;
		this._cursor = new PointF();
		buildGrid();
	}

	public ArrayList<ArrayList<Float>> getGrid() {
		return _grid;
	}
	
	public int getNbLines() {
		return _nbLines;
	}
	
	public int getMinCols() {
		return _minCols;
	}
	
	public int getMaxCols() {
		return _maxCols;
	}
	
	public double getMinSize() {
		return _minSize;
	}

	public double getMaxSize() {
		return _maxSize;
	}
	
	public double getHeightLine() {
		return _heightLine;
	}

	
	public double getIterLine() {
		return _iterLine;
	}
	
	public double getOffset() {
		return _offset;
	}
	
	public PointF getCursor(){
		return _cursor;
	}
	
	public void setCursor(PointF cursor){
		_cursor = cursor;
	}

	private void buildGrid(){
		_grid = new ArrayList<ArrayList<Float>>(_nbLines);
		for(int i = 0; i<_nbLines; i++){
			
			int size = MathUtils.random(_minCols, _maxCols);
			ArrayList<Float> cols = new ArrayList<Float>(size);
			
			for(int j = 0; j<size; j++){
				cols.add(j, (float) MathUtils.random(_minSize, _maxSize));
			}
			_grid.add(i, cols);
		}
	}
	
}
