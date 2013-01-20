package com.ihm.hardware.thread;

import android.os.Handler;

public class Clock implements Runnable{

	// Handler permettant le rafraichissement
	Handler _handler = new Handler();
	
	// Fr�quence � appliquer
	int _frequency = 24;
	
	// Listener appel� � chaque rafraichissement
	OnClockListener _listener;
	
	boolean _finished = false;
	
	/**
	 * Constructeur 
	 * @param listener Listener qui sera appel� � chaque rafraichissement
	 * @param frequency Fr�quence � appliquer
	 */
	public Clock(OnClockListener listener, int frequency) {
		_listener = listener;
		_frequency = frequency;
	}

	/**
	 * Modifie la fr�quence
	 * @param frequency Nouvelle fr�quence � appliquer
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
	 * Rafraichit l'action en fonction de la fr�quence
	 */
	private void refresh(){
		_listener.onClock();
		_handler.postDelayed(this, 1000 / _frequency);
	}

}
