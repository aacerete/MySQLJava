import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 46990527d on 02/02/17.
 */
public class InsertData {

    public static void insertMovies(int ID, String ORIGINAL_TITLE, String RELEASE_DATE) {

        Connection c = null;

        try {

            // Conectamos con la base de dat0s
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://172.31.73.191/world?useSSL=true", "root", "aacerete");


            //insertam0s
            String sql_insert = "INSERT INTO MOVIES" +
                    " (ID,ORIGINAL_TITLE,RELEASE_DATE) VALUES" +
                    " (?, ?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, ORIGINAL_TITLE);
            preparedStatement.setString(3, RELEASE_DATE);

            //ejecutamos insert
            preparedStatement.executeUpdate();

            preparedStatement.close();
            c.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertActor(int ID, int ID_PELICULA, String NAME, String CHARACTER) {

        Connection c = null;

        try {

            // Conectamos con la base de dat0s
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://172.31.73.191/world?useSSL=true", "root", "aacerete");


            //insertam0s
            String sql_insert = "INSERT INTO ACTORS" +
                    " (ID,ID_PELICULA,NAME,CHARACTER) VALUES" +
                    " (?, ?, ?,?);";


            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, ID_PELICULA);
            preparedStatement.setString(3, NAME);
            preparedStatement.setString(4, CHARACTER);


            //ejecutamos insert
            preparedStatement.executeUpdate();

            preparedStatement.close();
            c.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
