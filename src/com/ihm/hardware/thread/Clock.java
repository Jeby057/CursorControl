package com.ihm.hardware.thread;

import android.os.Handler;

public class Clock implements Runnable{

	// Handler permettant le rafraichissement
	Handler _handler = new Handler();
	
	// Fréquence à appliquer
	int _frequency = 24;
	
	// Listener appelé à chaque rafraichissement
	OnClockListener _listener;
	
	boolean _finished = false;
	
	/**
	 * Constructeur 
	 * @param listener Listener qui sera appelé à chaque rafraichissement
	 * @param frequency Fréquence à appliquer
	 */
	public Clock(OnClockListener listener, int frequency) {
		_listener = listener;
		_frequency = frequency;
	}

	/**
	 * Modifie la fréquence
	 * @param frequency Nouvelle fréquence à appliquer
	 */
	public void setFrequency(int frequency){
		_frequency = frequency;
	}
	
	@Override
	public void run() {
		if(!_finished)
			refresh();
	}
	
	public void start() {
		_finished = false;
		run();
	}
	
	public void stop() {
		_finished = true;
	}
	
	/**
	 * Rafraichit l'action en fonction de la fréquence
	 */
	private void refresh(){
		_listener.onClock();
		_handler.postDelayed(this, 1000 / _frequency);
	}

}
