package fr.capgemini.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.webservices.common.APIException;
import fr.capgemini.webservices.navitia.NavitiaInfosUrl;

public class TestIsochroneServlet {
	/**
	 * Juste une méthode main qui permet de tester.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 * @throws APIException
	 */
	private static  HttpServletRequest request;
	//private static  InfosUrlPourIsochrones mesInfos;
	private static  HttpServletResponse response;
	private static  NavitiaInfosUrl nav=new NavitiaInfosUrl();
	//NavitiaInfosUrl nav1=new NavitiaInfosUrl();

	public static void main(String[] args) {
IsochroneServlet test= new IsochroneServlet();
nav.setIntervalle(15);
nav.setMaxValue(30);
nav.setCodePostal("44000");
Coordonnees coord = new Coordonnees(1.5536, 47.2184);
nav.setCoord(coord);
nav.setDate(LocalDateTime.now());

System.out.println("->"+test.getServletConfig());


try {
	test.setCoordsFromUrl(request, nav, response);
}catch (Exception e) {
	System.out.println("+>"+e.getMessage());
}
		
}
}