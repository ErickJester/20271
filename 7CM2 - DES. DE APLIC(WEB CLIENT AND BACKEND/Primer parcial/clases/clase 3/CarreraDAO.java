// Clase CarreraDAO
// Se quedo hasta aqui al final de la Clase 3: solo la constante del INSERT
// y el metodo para conectarse a la base de datos. El CRUD (insertar,
// actualizar, eliminar, buscar) se vio hasta la clase siguiente, asi que
// no esta en este archivo.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CarreraDAO {

    // --- Constante con el SQL del INSERT ---
    // private: solo esta clase la necesita.
    // static: existe una sola copia compartida por toda la clase, no una por
    // cada objeto CarreraDAO que se cree.
    // final: no se puede reasignar despues de esta linea.
    // Java no tiene la palabra "const" (viene de C y esta reservada pero no
    // se puede usar); esta combinacion (private static final) es lo mas
    // cercano que el lenguaje ofrece a una constante.
    //
    // Los signos de interrogacion (?) son los espacios para los valores que
    // se van a insertar. No van escritos aqui porque eso es "bronca del
    // usuario": no importa si vienen de una app de escritorio, web, movil o
    // consola, esta consulta se queda igual.
    private static final String SQL_INSERT =
        "INSERT INTO Carrera (nombreCarrera, descripcionCarrera) VALUES (?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE Carrera SET nombreCarrera = ?, descripcionCarrera = ? WHERE idCarrera = ?";
    private static final String SQL_DELETE =
        "DELETE FROM Carrera WHERE idCarrera = ?";
    private static final String SQL_SELECT =
        "SELECT * FROM Carrera WHERE idCarrera = ?";
    private static final String SQL_SELECT_ALL =
        "SELECT * FROM Carrera";
    private List obtenerResultado(ResultSet rs) throws SQLException {
        List r = new ArrayList();
        while (rs.next()) {
            Carrera c = new Carrera();
            c.setIdCarrera(rs.getInt("idCarrera"));
            c.setNombreCarrera(rs.getString("nombreCarrera"));
            c.setDescripcionCarrera(rs.getString("descripcionCarrera"));
            r.add(c);
        }
        return r;
    }
    
        // Guarda la conexion activa a la base de datos una vez que se obtiene.
    private Connection conexion;

    // --- Metodo para conectarse ---
    // Por ahora es void: en teoria deberia devolver algo (un boolean o un
    // entero) para que quien lo llame sepa si la conexion si se logro o no,
    // pero se dejo asi por el momento.
    private void obtenerConexion() {

        // El usuario y la clave de la base de datos. Para fines de practica
        // se usa "root", pero lo correcto seria crear un usuario especifico
        // con permisos limitados para cada base de datos.
        String usuario = "root";
        String clave = "tu_password";

        // La cadena de conexion (URL) se arma asi:
        // jdbc:  -> se esta usando JDBC
        // mysql: -> el manejador es MySQL
        // localhost:3306 -> servidor y puerto (3306 es el de MySQL por defecto)
        // /MisCursos -> el nombre de la base de datos
        String urlBD = "jdbc:mysql://localhost:3306/MisCursos";

        // El driver especifico de MySQL. Desde JDBC 4 ya no es obligatorio
        // cargarlo a mano con Class.forName() antes de conectar (el
        // descubrimiento automatico de drivers lo hace innecesario), pero
        // se escribe aqui de todos modos.
        String driverMySQL = "com.mysql.cj.jdbc.Driver";

        try {
            // Registra el driver.
            Class.forName(driverMySQL);

            // Pide la conexion a DriverManager, que se encarga de buscar
            // cual driver sabe hablar con la URL que se le paso.
            conexion = DriverManager.getConnection(urlBD, usuario, clave);

        } catch (ClassNotFoundException | SQLException e) {
            // Dos excepciones distintas, ambas "checked" (el compilador
            // obliga a atraparlas): ClassNotFoundException si no encuentra
            // la clase del driver, SQLException si falla cualquier otra
            // parte de la conexion (usuario, clave, nombre de base, puerto).
            // La barra vertical entre las dos se llama multi-catch.
            //
            // Ojo: esto imprime el stack trace completo en la consola, que
            // no es buena practica para produccion (se vio en la misma
            // clase). Lo correcto es mandarlo a un log en vez de imprimirlo
            // aqui, pero asi quedo por el momento.
            e.printStackTrace();
        }
    }

    // Aqui se quedo la clase al terminar la sesion.
    // Lo que sigue (insertar, actualizar, eliminar, buscarPorId,
    // listarTodas) se construyo despues, usando PreparedStatement sobre
    // esta misma conexion.

    public void create (Carrera c) throws SQLException {
        // Metodo para insertar una nueva carrera en la base de datos.
        obtenerConexion();
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombreCarrera());
            ps.setString(2, c.getDescripcionCarrera());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void update (Carrera c) throws SQLException {
        // Metodo para actualizar una carrera existente en la base de datos.
        obtenerConexion();
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getNombreCarrera());
            ps.setString(2, c.getDescripcionCarrera());
            ps.setInt(3, c.getIdCarrera());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void delete (Carrera c) throws SQLException {
        // Metodo para eliminar una carrera existente en la base de datos.
        obtenerConexion();
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setInt(1, c.getIdCarrera());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public List readAll() throws SQLException {
        // Metodo para leer todas las carreras existentes en la base de datos.
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        // List resultado = null
        List<Carrera> carreras = new ArrayList<>();
        try{
            ps = conexion.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            //resultado = obtenerResultado(rs);
            
            if (resultado.size() > 0) {
                return resultado;
            } else {
                return null;
            }
        //}finally {
            //if (rs != null) {
                //rs.close();
            //}    
            //if (ps != null) {
              //  ps.close();
            //}
            //if (conexion != null) {
          //      conexion.close();
        //    }
      //  }
    //}
            while (rs.next()) {
                Carrera c = new Carrera();
                c.setIdCarrera(rs.getInt("idCarrera"));
                c.setNombreCarrera(rs.getString("nombreCarrera"));
                c.setDescripcionCarrera(rs.getString("descripcionCarrera"));
                carreras.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
        return carreras;
    }

    public Carrera read(Carrera c) throws SQLException {
        // Metodo para leer una carrera existente en la base de datos.
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List resultado = null
        try{
            ps = conexion.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, c.getIdCarrera());
            rs = ps.executeQuery();
            resultado = obtenerResultado(rs);
            
            if (resultado.size() > 0) {
                return resultado;
            } else {
                return null;
            }
        }finally {
            if (rs != null) {
                rs.close();
            }    
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
}
