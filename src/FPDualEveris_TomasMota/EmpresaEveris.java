package FPDualEveris_TomasMota;

import java.sql.*;

public class EmpresaEveris {
	 private static Connection conexion = null;
	    private static String bd = "tmota"; // Nombre de BD.
	    private static String user = "root"; // Usuario de BD.
	    private static String password = "root"; // Password de BD.
	    // Driver para MySQL en este caso.
	    private static String driver = "com.mysql.jdbc.Driver";
	    // Ruta del servidor.
	    private static String server = "jdbc:mysql://localhost/" + bd;
	 
	    public static void main(String[] args) throws SQLException {
	 
	        System.out.println("INICIO DE EJECUCIÓN.");
	        conectar();
	        Statement statement = conexion();
	 
	        // Se elimina la tabla "personal" en caso de existir.
	        String cadena = "DROP TABLE IF EXISTS personal;";
	        consultaActualiza(statement, cadena);
	 
	        // Se crea la tabla "personal"
	        cadena = "CREATE TABLE personal (`Identificador` int(11) NOT NULL AUTO_INCREMENT, `Nombre` varchar(50) NOT NULL, `Apellidos` varchar(50) NOT NULL, `Telefono` varchar(9) DEFAULT NULL, `Email` varchar(60) DEFAULT NULL, PRIMARY KEY (`Identificador`))";
	        consultaActualiza(statement, cadena);
	 
	        // Se crean datos de prueba para utilizarlos en la tabla "personal"
	        cadena = "INSERT INTO personal (`Identificador`, `Nombre`, `Apellidos`, `Telefono`, `Email`) VALUES (1, 'Agustin', 'Guerrero Reyes', '968112233', 'agustinguerrero@everis.com'), (2, 'Francisco Javier', 'Luna Palma', '911876876', 'franciscojavierluna@everis.com'), (3, 'Juan Jesús', 'Fernández ', '922111333', 'juanjesusfernandez@everis.com'), (4, 'Juan Alejandro', 'Tellez Rubio', '950999888', 'juan.alejandro.tellez@everis.com');";
	        consultaActualiza(statement, cadena);
	 
	        // Se sacan los datos de la tabla personal
	        cadena = "SELECT * FROM personal;";
	        ResultSet result = consultaQuery(statement, cadena);
	        if (result != null) {
	            System.out.println("El listado de persona es el siguiente:");
	 
	            while (result.next()) {
	                System.out.println("  ID: " + result.getObject("Identificador"));
	                System.out.println("  Nombre completo: "
	                        + result.getObject("Nombre") + " "
	                        + result.getObject("Apellidos"));
	 
	                System.out.println("  Contacto: " + result.getObject("Telefono")
	                        + " " + result.getObject("Email"));
	 
	                System.out.println("- ");
	            }
	            cerrarConsulta(result);
	        }
	        cerrarConexion(statement);
	        System.out.println("FIN DE EJECUCIÓN.");
	    }
	 
	    /**
	     * Método neecesario para conectarse al Driver y poder usar MySQL.
	     */
	    public static void conectar() {
	        try {
	            Class.forName(driver);
	            conexion = DriverManager.getConnection(server, user, password);
	        } catch (Exception error) {
	            System.out.println("Error: Imposible realizar la conexion a BD.");
	            error.printStackTrace();
	        }
	    }
	 
	    /**
	     * Método para establecer la conexión con la base de datos.
	     *
	     * @return
	     */
	    private static Statement conexion() {
	        Statement statement = null;
	        try {
	            statement = conexion.createStatement();
	        } catch (SQLException error) {
	            System.out.println("Error: Conexión incorrecta.");
	            error.printStackTrace();
	        }
	        return statement;
	    }
	 
	    /**
	     * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."
	     *
	     * @param st
	     * @param cadena La consulta en concreto
	     * @return
	     */
	    private static ResultSet consultaQuery(Statement statement, String cadena) {
	        ResultSet result = null;
	        try {
	            result = statement.executeQuery(cadena);
	        } catch (SQLException error) {
	            System.out.println("Error con: " + cadena);
	            System.out.println("SQLException: " + error.getMessage());
	            error.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * Método para realizar consultas de actualización, creación o eliminación.
	     *
	     * @param st
	     * @param cadena La consulta en concreto
	     * @return
	     */
	    private static int consultaActualiza(Statement statement, String cadena) {
	        int result = -1;
	        try {
	            result = statement.executeUpdate(cadena);
	        } catch (SQLException error) {
	            System.out.println("Error con: " + cadena);
	            System.out.println("SQLException: " + error.getMessage());
	            error.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * Método para cerrar la consula
	     *
	     * @param rs
	     */
	    private static void cerrarConsulta(ResultSet result) {
	        if (result != null) {
	            try {
	                result.close();
	            } catch (Exception error) {
	                System.out.print("Error: No es posible cerrar la consulta.");
	            }
	        }
	    }
	 
	    /**
	     * Método para cerrar la conexión.
	     *
	     * @param st
	     */
	    private static void cerrarConexion(java.sql.Statement statement) {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (Exception error) {
	                System.out.print("Error: No es posible cerrar la conexión.");
	            }
	        }
	    }

}
