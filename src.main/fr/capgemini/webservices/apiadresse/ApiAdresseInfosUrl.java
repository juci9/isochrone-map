package fr.capgemini.webservices.apiadresse;

import java.io.Serializable;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import fr.capgemini.model.Adresse;

/**
 * Classe contenant les informations nécessaires pour fabriquer une url pour
 * l'api adresse data gouv
 * 
 * @author aulecoin
 *
 */
public class ApiAdresseInfosUrl implements Serializable {
	
	
	ch.qos.logback.classic.Logger rollingFileLogger 
	 = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("rollingFileLogger");
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3887572069792930279L;
	private Adresse adresse;

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public ApiAdresseInfosUrl(Adresse adresse) {
		super();
		this.adresse = adresse;
	}

	public ApiAdresseInfosUrl() {

	}
public void main(String[] args) {

	rollingFileLogger.setLevel(Level.DEBUG);
	rollingFileLogger.debug("Testing Log Level");
}
}
