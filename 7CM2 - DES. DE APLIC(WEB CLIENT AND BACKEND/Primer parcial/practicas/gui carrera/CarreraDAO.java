// Clase CarreraDAO — version corregida y completa, tal como quedo el pizarron
// de la Clase 4 (los 5 metodos del CRUD).
//
// Dos diferencias a proposito respecto a lo que se escribio en el pizarron,
// pensadas para que esta GUI las pueda usar sin sobresaltos:
//
//   1. obtenerConexion() atrapa el ClassNotFoundException adentro y lo
//      envuelve en un SQLException. Asi los 5 metodos publicos solo
//      declaran "throws SQLException" — igual que en el pizarron — y quien
//      los llama (la GUI) no tiene que atrapar dos excepciones distintas.
//
//   2. readAll() devuelve una lista vacia cuando no hay registros, nunca
//      null. En el pizarron devolvia null si la lista estaba vacia; para
//      una tabla de Swing eso obliga a revisar null en todos lados antes
//      de recorrerla. Una lista vacia se recorre igual sin romperse.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

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

    private Connection conexion;

    private void obtenerConexion() throws SQLException {
        String usuario = "root";
        String clave = "tu_password";
        String urlBD = "jdbc:mysql://localhost:3306/MisCursos";
        String driverMySQL = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driverMySQL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL: " + driverMySQL, e);
        }
        conexion = DriverManager.getConnection(urlBD, usuario, clave);
    }

    public void create(Carrera c) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombreCarrera());
            ps.setString(2, c.getDescripcionCarrera());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    public void update(Carrera c) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getNombreCarrera());
            ps.setString(2, c.getDescripcionCarrera());
            ps.setInt(3, c.getIdCarrera());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    public void delete(Carrera c) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setInt(1, c.getIdCarrera());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    public List<Carrera> readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            return obtenerResultados(rs);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    public Carrera read(Carrera c) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT);
            ps.setInt(1, c.getIdCarrera());
            rs = ps.executeQuery();
            List<Carrera> resultados = obtenerResultados(rs);
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    private List<Carrera> obtenerResultados(ResultSet rs) throws SQLException {
        List<Carrera> r = new ArrayList<>();
        while (rs.next()) {
            Carrera c = new Carrera();
            c.setIdCarrera(rs.getInt("idCarrera"));
            c.setNombreCarrera(rs.getString("nombreCarrera"));
            c.setDescripcionCarrera(rs.getString("descripcionCarrera"));
            r.add(c);
        }
        return r;
    }
}
