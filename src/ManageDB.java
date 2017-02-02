import java.sql.*;
import java.util.Scanner;

/**
 * Created by 46990527d on 02/02/17.
 */
public class ManageDB {

    private static Connection Conexion;

    public static void main(String[] args) throws Exception {

        int option = 99;
        Scanner sc = new Scanner(System.in);


        //iniciamos el manager para la gestion de la BBDD
        ManageDB manager = new ManageDB();
        //conectamos
        Conexion = getConnection();
;

        do{

            System.out.println("Menu de gestión de la base de Datos World");
            System.out.println("------------------------------------------");
            System.out.println("1. Mostrar informacion de la BBDD (metadatos)");
            System.out.println("2. Realizar consultas");
            System.out.println("3. Insertar nueva tabla");
            System.out.println("4. Modificar actualizar tablas existentes");
            System.out.println("0. Salir");

            System.out.println("->");
            option = sc.nextInt();

            switch (option){
                case 1:
                    manager.getMetadatos();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    manager.closeConnection();
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }


        }while (option!=0);

        System.out.println("Saliendo...");

    }



    public static Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://172.31.73.191/world?useSSL=true";
        String username = "root";
        String password = "aacerete";
        Class.forName(driver);
        Conexion = DriverManager.getConnection(url, username, password);
        System.out.println("Conexión establecida...\n");
        return Conexion;
    }

    public void closeConnection() {
        try {
            Conexion.close();
            System.out.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {

        }
    }

    public void createTable(String name) throws Exception {

        try {
            String Query = "CREATE TABLE " + name + ""
                    + "(ID VARCHAR(25),Nombre VARCHAR(50), Apellido VARCHAR(50),"
                    + " Edad VARCHAR(3), Sexo VARCHAR(1))";

            Conexion = getConnection();
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);

            System.out.println("Se ha creado la tabla " + name + " correctamente");

        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla " + name);
        }
    }

    public void insertData(String table_name, String ID, String name, String lastname, String age, String gender) {

        try {
            String Query = "INSERT INTO " + table_name + " VALUES("
                    + "\"" + ID + "\", "
                    + "\"" + name + "\", "
                    + "\"" + lastname + "\", "
                    + "\"" + age + "\", "
                    + "\"" + gender + "\")";
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            System.out.println("Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            System.out.println("Error en el almacenamiento de datos");
        }
    }

    public void getValues(String table_name) {
        try {
            String Query = "SELECT * FROM " + table_name;
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getString("ID") + " "
                        + "Nombre: " + resultSet.getString("Nombre") + " " + resultSet.getString("Apellido") + " "
                        + "Edad: " + resultSet.getString("Edad") + " "
                        + "Sexo: " + resultSet.getString("Sexo"));
            }

        } catch (SQLException ex) {
            System.out.println("Error en la adquisición de datos");
        }
    }

    public void getMetadatos() {

        try {
            DatabaseMetaData meta = Conexion.getMetaData();
            System.out.println("El SGBD es : "+meta.getDatabaseProductName() + " version " + meta.getDatabaseProductVersion()+"\n");

            ResultSet rs = meta.getTables(null, null, "%", null);
            System.out.println("Tablas contenidas:");
            System.out.println("-------------------");
            while (rs.next()) {

                System.out.println(rs.getString(3));
            }
            System.out.println("-------------------\n");

            Conexion.close();
        } catch
                (SQLException ex) {
            System.out.println("SQLException :  " + ex.getMessage());
            System.out.println("SQLState :  " + ex.getSQLState());



        }
    }
}