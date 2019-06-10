package fr.capgemini.webservices.common;

import fr.capgemini.model.Coordonnees;

/**
 * Objet pouvant contenir les éléments servant à fabriquer une url pour appeler une API contenant des informations
 * sur les isochrones
 * @author aulecoin
 *
 */
public abstract class IsochronesInfosUrl {
	private Coordonnees coord;
	private int maxValue;
	private int intervalle;
	
	public Coordonnees getCoord() {
		return coord;
	}
	public void setCoord(Coordonnees coord) {
		this.coord = coord;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	public int getIntervalle() {
		return intervalle;
	}
	public void setIntervalle(int intervalle) {
		this.intervalle = intervalle;
	}
	public IsochronesInfosUrl() {
		
	}

	
	
	
	
}
