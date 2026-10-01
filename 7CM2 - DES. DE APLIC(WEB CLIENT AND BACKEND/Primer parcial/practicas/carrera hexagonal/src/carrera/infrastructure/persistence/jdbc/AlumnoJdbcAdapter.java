package carrera.infrastructure.persistence.jdbc;

import carrera.domain.exception.PersistenciaException;
import carrera.domain.port.out.AlumnoRepositoryPort;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// ADAPTADOR DE SALIDA para el puerto de alumnos: cuenta con un stored procedure.
public class AlumnoJdbcAdapter implements AlumnoRepositoryPort {

    private final String url;
    private final String usuario;
    private final String clave;

    public AlumnoJdbcAdapter(String url, String usuario, String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    @Override
    public int contarInscritosEnCarrera(int idCarrera) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, usuario, clave);
                 CallableStatement cs = con.prepareCall("{call sp_contar_inscritos_carrera(?)}")) {
                cs.setInt(1, idCarrera);
                try (ResultSet rs = cs.executeQuery()) {
                    return rs.next() ? rs.getInt("total") : 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new PersistenciaException("No se pudo contar los alumnos inscritos: " + e.getMessage(), e);
        }
    }
}
