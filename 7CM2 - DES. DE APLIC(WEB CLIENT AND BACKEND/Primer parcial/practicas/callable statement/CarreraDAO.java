// Clase CarreraDAO -- version con CallableStatement.
//
// Mismos 5 metodos publicos que la version con PreparedStatement de la
// practica "gui carrera", pero cada uno llama a un stored procedure en vez
// de mandar el SQL desde Java (init/02-procedimientos.sql tiene los 5).
// VentanaCarrera y Carrera no cambian nada: siguen llamando a estos mismos
// 5 metodos, sin saber que ahora hay procedimientos almacenados detras.

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

    private Connection conexion;

    private void obtenerConexion() throws SQLException {
        String usuario = "root";
        String clave = "tu_password";
        String urlBD = "jdbc:mysql://localhost:3307/MisCursos";
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
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall("{call sp_crear_carrera(?, ?)}");
            cs.setString(1, c.getNombreCarrera());
            cs.setString(2, c.getDescripcionCarrera());
            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (conexion != null) conexion.close();
        }
    }

    public void update(Carrera c) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall("{call sp_actualizar_carrera(?, ?, ?)}");
            cs.setInt(1, c.getIdCarrera());
            cs.setString(2, c.getNombreCarrera());
            cs.setString(3, c.getDescripcionCarrera());
            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (conexion != null) conexion.close();
        }
    }

    public void delete(Carrera c) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall("{call sp_eliminar_carrera(?)}");
            cs.setInt(1, c.getIdCarrera());
            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (conexion != null) conexion.close();
        }
    }

    public List<Carrera> readAll() throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = conexion.prepareCall("{call sp_listar_carreras()}");
            rs = cs.executeQuery();
            return obtenerResultados(rs);
        } finally {
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (conexion != null) conexion.close();
        }
    }

    public Carrera read(Carrera c) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = conexion.prepareCall("{call sp_leer_carrera(?)}");
            cs.setInt(1, c.getIdCarrera());
            rs = cs.executeQuery();
            List<Carrera> resultados = obtenerResultados(rs);
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (cs != null) cs.close();
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
