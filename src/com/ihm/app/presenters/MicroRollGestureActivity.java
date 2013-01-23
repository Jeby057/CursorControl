package com.ihm.app.presenters;

import java.text.DecimalFormat;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ihm.app.graphics.CursorTestView;
import com.ihm.app.graphics.MicroRollGestureView;
import com.ihm.cursorcontrol.R;
import com.ihm.gestures.MicroRollGesture;
import com.ihm.gestures.OnGestureListener;
import com.ihm.hardware.thread.Clock;
import com.ihm.hardware.thread.OnClockListener;
import com.ihm.maths.MathUtils;
import com.ihm.maths.mapping.CustomExponentialMappingFunction;
import com.ihm.maths.mapping.MappingFunction;
import com.ihm.maths.pseudohaptic.PseudoHapticProjection;

public class MicroRollGestureActivity extends Activity implements OnGestureListener, OnClockListener{

	private MicroRollGestureView _gestureView;
	private MicroRollGesture _gesture;
	
	private CursorTestView _renderView;
	
	private Clock _gestureClock;
	
	private MappingFunction _gestureMapping;
	
	private PseudoHapticProjection _pseudoHaptiqueProjection;
	
	boolean _showReticule = true;
	
	float _scale = 0;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Création d'une 'clock' permettant de candencer les évènements 
		// des mouvements des micro-rolls à 25fps.
		_gestureClock = new Clock(this, 25);
		
		// Aide pseudo haptique
		_pseudoHaptiqueProjection = new PseudoHapticProjection(15);
		
		// Création d'une fonction de mapping qui permettra d'interpoler le micro roll
		_gestureMapping = new CustomExponentialMappingFunction();
		
		// Création d'un detecteur de gestes de Micro Roll
		_gesture = new MicroRollGesture(this);
		
		// Récupération de la vue GestureView et abonnement au controller de geste de micro-roll
		_gestureView = (MicroRollGestureView) findViewById(R.id.uiGesture);
		_gestureView.setOnTouchListener(_gesture);
		
		// Récupération de la vue Render
		_renderView = (CursorTestView) findViewById(R.id.uiRender);
		
		// Récupère la densité : 
		_scale = getResources().getDisplayMetrics().xdpi / 345.0566F;
		
		// Paramétrage des vues
		_gestureView.setCircleOutRadius(70 *_scale);
		_gestureView.setCircleInRadius(25 * _scale);
		_gestureView.setOffset(new PointF(- 255 * _scale, 0));
		_gestureView.setTextInfoSize(20 * _scale);
		
		_renderView.setNbLines(15);
		_renderView.setMinCols(5);
		_renderView.setMinColSize(15 * _scale);
		_renderView.setMaxColSize(30 * _scale);
		_renderView.setOffset(15 * _scale);
		_renderView.setHeightLine(35 * _scale);
		_renderView.setSpacing(12 * _scale);
		_renderView.setReticuleSize(50 * _scale);
		_renderView.setGridFractal(10);
		_renderView.setCursorLocation(new PointF(_renderView.getOffset(), _renderView.getOffset()));
		_renderView.setShowReticule(true);
		
		// Paramétrage du geste
		_gesture.setScope(70 * _scale);
		_gesture.setNullAreaSize(25 * _scale);
		
		// Paramétrage de la fonction de mapping
		_gestureMapping.setSensibility(0.18F);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.itMenuShowCursor:
	        	_showReticule = !_showReticule;
	        	_renderView.setShowReticule(_showReticule);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	protected void onResume() {

		// Démarrage de la cadence
		_gestureClock.start();
		_renderView.autoplaceResticule();
				
		super.onResume();
	}
	
	
	
	@Override
	protected void onPause() {
		
		_gestureClock.stop();
		super.onPause();
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
		//if(_showReticule)
		//	_renderView.setShowReticule(true);
		_gestureView.setPrint(true);
		_renderView.autoplaceResticule();
	}
	
	@Override
	public void onStopGesture() {
		//if(_showReticule)
			//_renderView.setShowReticule(false);
		
		_gestureView.setPrint(false);
	}
	
	@Override
	public void onGesture() {
		// Rien à faire
	}
	
	public void update(){
		
		// Application de la fonction de mapping
		float microroll = _gestureMapping.execute(_gesture.getMicroRollValue() / _scale) * _scale;

		// Application d'une aide pseudo haptique
		PointF direction = _pseudoHaptiqueProjection.projection(_gesture.getMicroRollDirection());
		
		// Zone de micro roll
			// Position de la zone de micro roll
			_gestureView.setCircleLocation(_gesture.getFirstPosition());
			
			// Position de la cible définit par le micro roll courant
			_gestureView.setPointerLocation(_gesture.getPosition());
			
			// Insertions des informations sur le micro-rolls
			DecimalFormat format = new DecimalFormat("##0.00");
			_gestureView.setInfos(new String[]{
					" Scale :" + _scale, 
					" Original MicroRoll : ", 
					" - Value : " + format.format(_gesture.getMicroRollValue())+ "px", 
					" - Map Value : " + format.format(_gesture.getMicroRollValue() / _scale)+ "px", 
					" - Direction : ",  
					"   - X : " +format.format(_gesture.getMicroRollDirection().x)+"px", 
					"   - Y : " +format.format(_gesture.getMicroRollDirection().y)+"px",
					"  ", 
					" Modified MicroRoll : ", 
					" - Value :" + format.format(microroll)+ "px",
					" - Map Value :" + format.format(microroll / _scale)+ "px", 
					" - Direction :",  
					"   - X : " +format.format(direction.x)+"px", 
					"   - Y : " +format.format(direction.y)+"px",
					"  ", 
					" Reticule location : ", 
					"   - X : " +format.format(_renderView.getReticuleLocation().x)+"px", 
					"   - Y : " +format.format(_renderView.getReticuleLocation().y)+"px",
					"   - Displayed : " +(_renderView.isShowReticule()?"Y":"N"),
					"  ", 
					" Cursor location : ", 
					"   - X : " +format.format(_renderView.getCursorLocation().x)+"px", 
					"   - Y : " +format.format(_renderView.getReticuleLocation().y)+"px"});
		
			// On applique les modifications
			_gestureView.invalidate();
		
		// Vue de test du curseur
			
			// Translation
			_renderView.translateReticule(MathUtils.factor(direction, microroll));
			
			// On applique les modifications
			_renderView.invalidate();
	}

	

	

	

	

}
