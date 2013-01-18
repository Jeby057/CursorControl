package com.ihm.renders.simple;

import android.graphics.PointF;

import com.ihm.maths.mapping.MappingFunction;
import com.ihm.renders.RenderControl;

public class SimpleRender extends RenderControl{

	public SimpleRender(MappingFunction mappingFct) {
		super(mappingFct);
	}

	@Override
	public PointF update(PointF translate) {
		return super.update(translate);
	}

	
}
