package fr.capgemini.model;

/**
 * Moyens de transport pour OpenRouteService
 * 
 * @author aulecoin
 *
 */
public enum MoyenTransport {
	DRIVINGCAR("driving-car"), FOOTWALKING("foot-walking"), CYCLINGREGULAR("cycling-regular");

	private String moyen;

	private MoyenTransport(String moyen) {
		this.moyen = moyen;
	}

	@Override
	public String toString() {
		return moyen;
	}

}
