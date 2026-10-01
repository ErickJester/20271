package carrera.domain.model;

import carrera.domain.exception.CarreraException;

// DOMINIO: Java puro. Ni una anotacion, ni un import de java.sql o javax.swing.
// Las reglas de negocio viven aqui: una Carrera invalida no puede existir.
public class Carrera {

    public static final int MAX_NOMBRE = 150;
    public static final int MAX_DESCRIPCION = 500;

    private final Integer idCarrera; // null mientras no se haya guardado
    private final String nombreCarrera;
    private final String descripcionCarrera;

    public Carrera(Integer idCarrera, String nombreCarrera, String descripcionCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera == null ? null : nombreCarrera.trim();
        this.descripcionCarrera = descripcionCarrera == null ? null : descripcionCarrera.trim();
        validar();
    }

    private void validar() {
        if (nombreCarrera == null || nombreCarrera.isEmpty()) {
            throw new CarreraException("El nombre de la carrera es obligatorio");
        }
        if (descripcionCarrera == null || descripcionCarrera.isEmpty()) {
            throw new CarreraException("La descripcion de la carrera es obligatoria");
        }
        if (nombreCarrera.length() > MAX_NOMBRE) {
            throw new CarreraException("El nombre no puede pasar de " + MAX_NOMBRE + " caracteres");
        }
        if (descripcionCarrera.length() > MAX_DESCRIPCION) {
            throw new CarreraException("La descripcion no puede pasar de " + MAX_DESCRIPCION + " caracteres");
        }
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public String getDescripcionCarrera() {
        return descripcionCarrera;
    }
    
    public void verificarPuedeEliminarse(int alumnosInscritos){
        if (alumnosInscritos > 0) {
            throw new CarreraException("No se puede eliminar. Tiene " + alumnosInscritos + " alumnos inscritos.");
        }
    }

    @Override
    public String toString() {
        return "id: " + idCarrera + "\nnombre: " + nombreCarrera + "\ndescripcion: " + descripcionCarrera + "\n";
    }
}
