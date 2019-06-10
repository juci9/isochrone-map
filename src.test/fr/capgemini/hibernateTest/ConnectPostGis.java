package fr.capgemini.hibernateTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.postgis.PGgeometry;

public class ConnectPostGis {

/**
 * test de bonne connexion au driver jdbc et connexion à la base
 * @param args
 */
	public static void main_test_driver(String[] args) {      

	    try {
	      Class.forName("org.postgresql.Driver");
	      System.out.println("Driver O.K.");

	      String url = "jdbc:postgresql://localhost:5432/TestDB";
	      String user = "postgres";
	      String passwd = "Oklevel19879";
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("Connexion effective !");         

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  
	/**
	 * test de connexion à la base avec un ordre select sur la géométrie
	 * @param args
	 */
	  public static void main(String[] args) { 

		  java.sql.Connection conn; 

		  try { 
		    /* 
		    * Load the JDBC driver and establish a connection. 
		    */
		    Class.forName("org.postgresql.Driver"); 
		    String url = "jdbc:postgresql://localhost:5432/TestDB"; 
		    String user = "postgres";
		    String passwd = "Oklevel19879";
		      
		    conn = DriverManager.getConnection(url, user, passwd); 
		    /* 
		    * Add the geometry types to the connection. Note that you 
		    * must cast the connection to the pgsql-specific connection 
		    * implementation before calling the addDataType() method. 
		    */
		    ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
		    ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));

		    /* 
		    * Create a statement and execute a select query. 
		    */ 
		    Statement s = conn.createStatement(); 
		    ResultSet r = s.executeQuery("select geom,id from isochrone"); 
		    while( r.next() ) { 
		      /* 
		      * Retrieve the geometry as an object then cast it to the geometry type. 
		      * Print things out. 
		      */ 
		      PGgeometry geom = (PGgeometry)r.getObject(1); 
		      int id = r.getInt(2); 
		      System.out.println("Row " + id + ":");
		      System.out.println(geom.toString()); 
		    } 
		    s.close(); 
		    conn.close(); 
		  } 
		catch( Exception e ) { 
		  e.printStackTrace(); 
		  } 
		}
	  /*
	  
	  public static void main(String[] args) { 
		  

		  
			HttpServletRequest request;
			//private static  InfosUrlPourIsochrones mesInfos;
			 HttpServletResponse response;
			 NavitiaInfosUrl nav=new NavitiaInfosUrl();
			//NavitiaInfosUrl nav1=new NavitiaInfosUrl();
		  
		  
		  
		  
		  
		  
				IsochroneServlet test= new IsochroneServlet();
				nav.setIntervalle(15);
				nav.setMaxValue(30);
				nav.setCodePostal("44000");
				Coordonnees coord = new Coordonnees(1.5536, 47.2184);
				nav.setCoord(coord);
				nav.setDate(LocalDateTime.now());

					
				
		
		  
		  
		  
		  
		  
		  
		 /* 
		  java.sql.Connection conn;
	        try {
	            Class.forName("org.postgresql.Driver");
	            String url = "jdbc:postgresql://localhost:5432/postgis_24_sample";
	            conn = DriverManager.getConnection(url, "postgres", "manager");

	            ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
	            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));

	            Polygon geo = new Polygon(
	                            new LinearRing[] {
	                                new LinearRing(
	                                    new Point[] {
	                                        new Point(-1.0d, -1.0d,  0.5d),
	                                        new Point( 1.0d, -1.0d,  0.0d),
	                                        new Point( 1.0d,  1.0d, -0.5d),
	                                        new Point(-1.0d,  1.0d,  0.0d),
	                                        new Point(-1.0d, -1.0d,  0.5d)
	                                    }
	                                )
	                            }
	                        );
	            PreparedStatement s = conn.prepareStatement("INSERT INTO isochrone (key_value, large_poly) VALUES (?, ?)");
	            s.setString(1, "poly1");
	            s.setObject(2, new PGgeometry(geo));

	            int rows = s.executeUpdate();

	            if (rows > 0) {
	                System.out.println(" Successful insert! ");
	            } else {
	                System.out.println(" Failed insert!");
	            }
	            s.close();
	        } * /
	        
	  } */
}
