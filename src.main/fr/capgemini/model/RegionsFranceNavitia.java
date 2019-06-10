package fr.capgemini.model;

/**
 * Liste des régions prises en compte par Navitia
 * 
 * @author aulecoin
 *
 */
public enum RegionsFranceNavitia {
	BRETAGNE("fr-bre"), ILEDEFRANCE("fr-idf"), NORDEST("fr-ne"), AMIENS("fr-ne-amiens"), NORDOUEST("fr-nw"), CAEN(
			"fr-nw-caen"), SUDEST("fr-se"), SUDOUEST("fr-sw");

	private String region;

	private RegionsFranceNavitia(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return region;
	}
}
