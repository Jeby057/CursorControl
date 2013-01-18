package com.ihm.renders.grid;

import android.graphics.PointF;

import com.ihm.maths.mapping.MappingFunction;
import com.ihm.renders.RenderControl;

public class GridRender extends RenderControl{

	GridRenderData _data;
	
	public GridRender(MappingFunction mappingFct, GridRenderData data) {
		super(mappingFct);
	}

	@Override
	public PointF update(PointF translate) {
		return super.update(translate);
	}
	
	

}
