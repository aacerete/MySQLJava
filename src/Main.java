

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by 46990527d on 26/01/17.
 */
public class Main {

    /*

    iniciar servidor :sudo /etc/init.d/mysql start
    puerto defecto :3306

     bajar bbdd: wget http://downloads.mysql.com/docs/world.sql.gz
     descomprimir : gzip -d world.sql.gz

iniciar mysql: mysql -u root -p




     COMANDOS SQL: CREAR BBD: CREATE DATABASE world;
                        USAR : USE world;
     *               CREAR TABLA: CREATE TABLE persona (nombre VARCHAR(40), apellido VARCHAR(40));
      *               INSERTAR BDD desde archivo SQL :  SOURCE world.sql;
      *
      *               dar privilegios: grant all privileges on world.* to root@'152.31.73.15' identified by 'aacerete'
      *
      *
      *
      *               */


    public static void main(String[] args) {

        try{
            //Cargamos el driver mySQL
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // Establecemos la conexión con la base de datos.
        try {

            Connection conexion = DriverManager.getConnection("jdbc:mysql://172.31.73.191/world?useSSL=true", "root", "aacerete");

            // Preparamos las consultas y updates
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery ("select NAME from city where id=3");

            // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
            while (rs.next())
            {
                System.out.println (rs.getString (1)+ " " );
            }

            // Cerramos la conexion a la base de datos.
            conexion.close();


        }catch (Exception e){
            System.out.println("Error de conexion");
        }




    }
}
