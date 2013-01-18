package com.ihm.app.presenters;

import java.text.DecimalFormat;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;

import com.ihm.app.graphics.MicroRollGestureView;
import com.ihm.cursorcontrol.R;
import com.ihm.gestures.MicroRollGesture;
import com.ihm.gestures.OnGestureListener;
import com.ihm.hardware.thread.Clock;
import com.ihm.hardware.thread.OnClockListener;
import com.ihm.maths.mapping.LinearMappingFunction;
import com.ihm.maths.mapping.MappingFunction;
import com.ihm.renders.RenderControl;
import com.ihm.renders.grid.GridRender;
import com.ihm.renders.grid.GridRenderData;
import com.ihm.renders.grid.GridRenderView;

public class MicroRollGestureActivity extends Activity implements OnGestureListener, OnClockListener{

	private MicroRollGestureView _gestureView;
	private MicroRollGesture _gesture;
	
	private GridRenderView _renderView;
	private RenderControl _render;
	
	private Clock _gestureClock;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Création d'une 'clock' permettant de candencer les évènements 
		// des mouvements des micro-rolls à 25fps.
		_gestureClock = new Clock(this, 25);
		
		// Création d'un detecteur de gestes de Micro Roll
		_gesture = new MicroRollGesture(this);
		
		// Récupération de la vue GestureView et abonnement au controller de geste de micro-roll
		_gestureView = (MicroRollGestureView) findViewById(R.id.uiGesture);
		_gestureView.setOnTouchListener(_gesture);
		
		// Création d'un "moteur" de rendu
		GridRenderData data = new GridRenderData(15, 35, 12, 5, 20, 15, 30, 15);
		MappingFunction mapfct = new LinearMappingFunction();
		mapfct.setSensibility(0.25F);
		_render = new GridRender(mapfct, data);
		
		// Récupération de la vue Render
		_renderView = (GridRenderView) findViewById(R.id.uiRender);
		_renderView.setData(data);
		
		// Paramétrage
		_gestureView.setCircleOutRadius(50);
		_gestureView.setCircleInRadius(10);
		_gesture.setScope(50);
	}

	@Override
	protected void onResume() {

		// Démarrage de la cadence
		_gestureClock.run();
				
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClock() {
		update();
	}
	
	@Override
	public void onStartGesture() {
		_gestureView.setPrint(true);
		_renderView.resetResticule();
	}
	
	@Override
	public void onStopGesture() {
		_gestureView.setPrint(false);
	}
	
	@Override
	public void onGesture() {
		// Rien à faire
	}
	
	public void update(){
		// Zone de micro roll
			// Position de la zone de micro roll
			_gestureView.setCircleLocation(_gesture.getFirstPosition());
			
			// Position de la cible définit par le micro roll courant
			_gestureView.setPointerLocation(_gesture.getPosition());
			
			// Insertions des informations sur le micro-rolls
			DecimalFormat format = new DecimalFormat("###.00");
			_gestureView.setInfos(new String[]{"MicroRoll's values:", "x=" +format.format(_gesture.getMicroRoll().x)+ "px", "y=" +format.format(_gesture.getMicroRoll().y)+"px"});
		
			// On applique les modifications
			_gestureView.invalidate();
		
		// Vue de test du curseur
			
			// Translation 
			PointF translate = _render.update(_gesture.getMicroRoll());
			_renderView.translateReticule(translate);
			_renderView.invalidate();
	}

	

	

	

	

}
