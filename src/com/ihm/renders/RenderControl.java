package com.ihm.renders;

import android.graphics.PointF;

import com.ihm.maths.mapping.MappingFunction;

public abstract class RenderControl {

	protected MappingFunction _mappingFct;

	public RenderControl(MappingFunction mappingFct) {
		super();
		this._mappingFct = mappingFct;
	}
	
	public PointF update(PointF translate){
		return new PointF(_mappingFct.execute(translate.x), _mappingFct.execute(translate.y));
	}
	
}
