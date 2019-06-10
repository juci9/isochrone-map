package fr.capgemini.hibernateTest;

import org.hibernate.Session;
import org.postgis.Geometry;
import org.postgis.Point;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.model.Entreprise;
import fr.capgemini.utils.HibernateUtil;

public class HibernateConnexionTest {
	
	public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
 
        session.beginTransaction();
        
        Coordonnees coordDepart=new Coordonnees(-1.5982239872,47.2241697254);
        Point pt = new Point(coordDepart.getLongitude(), coordDepart.getLatitude());
		pt.setSrid(4326);
		
		Entreprise entr = new Entreprise ("Berlingot", "3 rue du Croissant","44000",(Geometry) pt);
		
		session.save(entr);
		session.getTransaction().commit();
		session.close();
    }

}
