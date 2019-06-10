package fr.capgemini.model;

public enum MessagesErreur {
	ERROR_SELECT_ADRESS("Problème avec la sélection dans la liste d'adresse proposée."), ERROR_COORDS_DEPART(
			"Problème avec la récupération des coordonnées du point de départ."), ERROR_MOYENS(
					"Problème avec la liste de moyens de transport."), ERROR_ISO_ORS(
							"Problème avec la récupération des isochrones de l'API ORS."), ERROR_APPEL_ORS(
									"Problème avec l'appel de l'API Open Route Service."), ERROR_APPEL_NAVITIA(
											"Problème avec l'appel de l'API Navitia."), ERROR_ISO_NAVITIA(
													"Problème avec la récupération des isochrones de l'API Navitia."), ERROR_FORM(
															"Veuillez respecter le format demandé et remplir tous les champs."), ERROR_GET_ADRESSES(
																	"Problème avec la récupération des listes d'adresses. "), ERROR_ADRESSE_NOT_FOUND(
																			"Adresse non trouvée en base, veuillez réessayer. ");

	private String message;

	private MessagesErreur(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
