import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Principal {
    public static void main (String[] args) {
        Carrera c = new Carrera();
        c.setIdCarrera(1);
        c.setNombreCarrera("Ingenieria en Sistemas");
        c.setDescripcionCarrera("Carrera de Ingenieria en Sistemas");
        
        System.out.println(c.toString());

        CarreraDAO dao = new CarreraDAO();
        try {
            dao.insert(c);
            System.out.println("Carrera insertada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la carrera: " + e.getMessage());
        }
    }
}