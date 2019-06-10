package fr.capgemini.webservices.ors;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.model.MoyenTransport;
import fr.capgemini.webservices.common.IsochronesInfosUrl;

/**
 * Classe contenant les informations pour fabriquer une Url OpenRouteservice
 * 
 * @author aulecoin
 *
 */
public class ORSInfosUrl extends IsochronesInfosUrl {
	private MoyenTransport moyen;

	public MoyenTransport getMoyen() {
		return moyen;
	}

	public void setMoyen(MoyenTransport moyen) {
		this.moyen = moyen;
	}

	public ORSInfosUrl() {
		super();
	}

	public ORSInfosUrl(Coordonnees coord, MoyenTransport moyen, int maxValue, int intervalle) {

		this.moyen = moyen;
		this.setCoord(coord);
		this.setMaxValue(maxValue);
		this.setIntervalle(intervalle);
	}

}
