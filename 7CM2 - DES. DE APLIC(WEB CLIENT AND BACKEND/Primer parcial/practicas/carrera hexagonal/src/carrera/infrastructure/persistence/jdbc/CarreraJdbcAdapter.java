package carrera.infrastructure.persistence.jdbc;

import carrera.domain.exception.PersistenciaException;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ADAPTADOR DE SALIDA: implementa el puerto con JDBC + stored procedures
// (es el antiguo CarreraDAO con CallableStatement). Todo lo "casado con
// MySQL" vive aqui y solo aqui; los SQLException se traducen a
// PersistenciaException para que no se escapen al dominio.
public class CarreraJdbcAdapter implements CarreraRepositoryPort {

    private final String url;
    private final String usuario;
    private final String clave;

    public CarreraJdbcAdapter(String url, String usuario, String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    private Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL", e);
        }
        return DriverManager.getConnection(url, usuario, clave);
    }

    @Override
    public void crear(Carrera c) {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call sp_crear_carrera(?, ?)}")) {
            cs.setString(1, c.getNombreCarrera());
            cs.setString(2, c.getDescripcionCarrera());
            cs.execute();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar la carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Carrera c) {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call sp_actualizar_carrera(?, ?, ?)}")) {
            cs.setInt(1, c.getIdCarrera());
            cs.setString(2, c.getNombreCarrera());
            cs.setString(3, c.getDescripcionCarrera());
            cs.execute();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar la carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int idCarrera) {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call sp_eliminar_carrera(?)}")) {
            cs.setInt(1, idCarrera);
            cs.execute();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo eliminar la carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Carrera> buscarPorId(int idCarrera) {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call sp_leer_carrera(?)}")) {
            cs.setInt(1, idCarrera);
            try (ResultSet rs = cs.executeQuery()) {
                List<Carrera> r = mapear(rs);
                return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo leer la carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Carrera> listar() {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call sp_listar_carreras()}");
             ResultSet rs = cs.executeQuery()) {
            return mapear(rs);
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo leer el catalogo de carreras: " + e.getMessage(), e);
        }
    }

    // fila de la BD -> objeto de dominio (el "mapper" de este adaptador)
    private List<Carrera> mapear(ResultSet rs) throws SQLException {
        List<Carrera> r = new ArrayList<>();
        while (rs.next()) {
            r.add(new Carrera(rs.getInt("idCarrera"),
                              rs.getString("nombreCarrera"),
                              rs.getString("descripcionCarrera")));
        }
        return r;
    }
}
