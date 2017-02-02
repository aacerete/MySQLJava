import java.sql.*;
import java.util.Scanner;

/**
 * Created by 46990527d on 02/02/17.
 */
public class ManageDB {

    /*

    Con este programa nos conectamos a la base de datos mySQL world.

    Para instalarla:
    Desde la maquina virtual:
            sudo apt-get mysql-server
            sudo apt-get mysql-client

    iniciar servidor: sudo /etc/init.d/mysql start
      puerto defecto: 3306

   bajar bbdd world : wget http://downloads.mysql.com/docs/world.sql.gz
       descomprimir : gzip -d world.sql.gz
       iniciar mysql: mysql -u root -p

     COMANDOS SQL:
     Crear BBD:   CREATE DATABASE world;
         Usar :   USE world
dar privilegios:  grant all privileges on world.* to root@'152.31.73.15' identified by 'aacerete'

         */

    // He creado una clase manager para controlar la gestion de la base de datos con un atributo connection.

    private static Connection Conexion;

    //main para utilizar el menú. Utilizando las opciones por orden, puede comprobarse la muestra de información, la creación
    //una tabla, el registro de datos en esa tabla, una select con el contenido de la misma, la modificación de registros
    //de la tabla y una nueva select para comprovar los cambios.

    public static void main(String[] args) throws Exception {

        int option = 99;
        Scanner sc = new Scanner(System.in);


        //iniciamos el manager para la gestion de la BBDD
        ManageDB manager = new ManageDB();
        //conectamos
        Conexion = getConnection();

        //ejecutamos el menu por primera vez
        do{

            System.out.println("Menu de gestión de la base de Datos World");
            System.out.println("------------------------------------------");
            System.out.println("1. Mostrar informacion de la BBDD (metadatos)");
            System.out.println("2. Insertar nueva tabla (persona) ");
            System.out.println("3. Insertar registros en tabla persona");
            System.out.println("4. Mostrar tabla persona");
            System.out.println("5. Modificar la tabla persona insetando datos");
            System.out.println("6. Volvemos a mostrar tabla para comprobar que los datos se han modificado");
            System.out.println("0. Salir");

            System.out.println("->");
            option = sc.nextInt();

            switch (option){
                case 1:
                    //mostramos metadatos
                    manager.getMetadatos();
                    break;
                case 2:
                    //eliminamos la tabla si está creada
                    manager.dropTable();
                    //la Creamos de nuevo
                    manager.createTable();
                    break;
                case 3:

                    //insertamos varios registros
                    manager.insertData("1","juan","lopez","22","m");
                    manager.insertData("2","luis","ruiz","42","m");
                    manager.insertData("3","carmen","garcia","28","f");
                    manager.insertData("4","juan","fernandez","62","m");
                    manager.insertData("5","luisa","perez","12","f");
                    break;
                case 4:
                    //realizamos una select para ver el contenido de la tabla creada con los datos insertados
                    manager.getValues();
                    break;
                case 5:
                    manager.updateValues();
                    break;
                case 6:
                    manager.getValues();
                case 0:
                    //ceramos la conexión y salimos
                    manager.closeConnection();
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }


        }while (option!=0);

        System.out.println("Saliendo...");

    }


    //método que establece la conexión con la base de datos segun los parametros (driver , url de la BBDD, usuario i password
    //Seteamos la conexion de la clase y la iniciamos
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


    //metodo que cierra la conexión
    public void closeConnection() {
        try {
            Conexion.close();
            System.out.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {

        }
    }

    //método para crear la tabla personas
    public void createTable() throws Exception {

        try {

            Statement st = Conexion.createStatement();


            String Query = "CREATE TABLE persona"
                    + "(ID VARCHAR(25),Nombre VARCHAR(50), Apellido VARCHAR(50),"
                    + " Edad VARCHAR(3), Sexo VARCHAR(1))";

            st.executeUpdate(Query);

            System.out.println("Se ha creado la tabla persona correctamente\n");

        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla persona\n");
        }
    }

    //método para eliminar la tabla persona
    public void dropTable() throws Exception {

        try {

            String Query = "DROP TABLE IF EXISTS persona";

            Conexion = getConnection();
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);

            System.out.println("Se ha eliminado la tabla persona correctamente\n");

        } catch (SQLException ex) {
            System.out.println("Error al eliminar la tabla persona\n");
            System.out.println(ex.getMessage());
        }
    }

    //método para insertar datos en la tabla persona
    public void insertData(String ID, String name, String lastname, String age, String gender) {

        try {
            String Query = "INSERT INTO persona VALUES("
                    + "\"" + ID + "\", "
                    + "\"" + name + "\", "
                    + "\"" + lastname + "\", "
                    + "\"" + age + "\", "
                    + "\"" + gender + "\")";
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            System.out.println("Datos almacenados de correctamente\n");
        } catch (SQLException ex) {
            System.out.println("Error en el almacenamiento de datos\n");
            ex.printStackTrace();
        }
    }

    //método que devuelve el contenido de la tabla persona
    public void getValues() {
        try {
            String Query = "SELECT * FROM persona";
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getString("ID") + " "
                        + "Nombre: " + resultSet.getString("Nombre") + "  Apellido; " + resultSet.getString("Apellido") + " "
                        + "Edad: " + resultSet.getString("Edad") + " "
                        + "Sexo: " + resultSet.getString("Sexo"));
            }
            System.out.println();

        } catch (SQLException ex) {
            System.out.println("Error en la adquisición de datos\n");
        }
    }

    //método que modifica el nombre de la persona con la ID: 1
    public void updateValues() {

        try {

            //Creamos la query
            String query = "update persona set nombre = ? where id= ?";
            //el prepared statement a partir de la quey
            PreparedStatement preparedStmt = Conexion.prepareStatement(query);
            //Cambiamos el nombre de la persona cuya ID es 1
            preparedStmt.setString  (1, "ANGEL");
            preparedStmt.setString(2, "1");

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            System.out.println("Cambio realizado con éxito\n");


        } catch (SQLException ex) {
            System.out.println("Error actualizando la tabla\n");
        }
    }



    //método que muestra metadatos y el contenido de la BBDD (tablas)
    public void getMetadatos() {

        try {
            //obtenemos los metadatos de la conexión
            DatabaseMetaData meta = Conexion.getMetaData();
            System.out.println("El SGBD es : "+meta.getDatabaseProductName() + " version " + meta.getDatabaseProductVersion()+"\n");

            //mostramos las tablas que contiene la BBDD
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