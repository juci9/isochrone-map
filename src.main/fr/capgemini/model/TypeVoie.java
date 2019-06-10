package fr.capgemini.model;

/**
 * Enumération contenant les types de voies possibles pour une adresse postale
 * Non exhaustif car l'API adresse data gouv se débrouille a priori plutôt bien
 * avec des erreurs sur les types de voie
 * 
 * @author aulecoin
 *
 */
public enum TypeVoie {
	ALLEE("allee"), AVENUE("avenue"), BOULEVARD("boulevard"), CHEMIN("chemin"), IMPASSE("impasse"), RUE("rue");

	private String voie;

	private TypeVoie(String voie) {
		this.voie = voie;
	}

	@Override
	public String toString() {
		return voie;
	}

}
