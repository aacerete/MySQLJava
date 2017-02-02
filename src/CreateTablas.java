import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by 46990527d on 02/02/17.
 */
public class CreateTablas {

    public static void main(String[] args) {

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://172.31.73.191/world?useSSL=true", "root", "aacerete");

            System.out.println("Base de Datos Creada\n");
            stmt = c.createStatement();


            //Creamos tablas
            String sql_movies = "CREATE TABLE MOVIES " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " ORIGINAL_TITLE TEXT    NOT NULL, " +
                    " RELEASE_DATE   TEXT    NOT NULL);";


            String sql_actor = "CREATE TABLE ACTORS " +
                    "(ID INT    NOT NULL," +
                    " ID_PELICULA   INT    NOT NULL," +
                    " NAME TEXT    NOT NULL, " +
                    " CHARACTER   TEXT    NOT NULL," +
                    "PRIMARY KEY (ID,ID_PELICULA));";

            stmt.executeUpdate(sql_movies);
            stmt.executeUpdate(sql_actor);
            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Tablas creadas\n");
    }

}
