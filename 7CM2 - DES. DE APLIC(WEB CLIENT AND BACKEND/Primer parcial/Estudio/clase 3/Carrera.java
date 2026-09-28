// Clase Carrera
// Reconstruida a partir del ejercicio del pizarron de la Clase 3.
// Sigue el diagrama UML: idCarrera (int), nombreCarrera (String), descripcionCarrera (String).

public class Carrera {

    // --- Atributos ---
    // Todos privados: es la parte de "encapsulamiento" que se explico en clase.
    // Nadie fuera de esta clase puede leerlos ni modificarlos directamente,
    // solo a traves de los mutators (set) y accessors (get) de mas abajo.
    private int idCarrera;
    private String nombreCarrera;
    private String descripcionCarrera;

    // --- Constructor vacio (con argumento cero) ---
    // Aunque no reciba nada, SI necesita llevar las llaves. Terminarlo en punto
    // y coma (como paso en el pizarron) lo convierte en una declaracion sin
    // cuerpo y el codigo no compila.
    // Es obligatorio declararlo a mano en cuanto se agregue cualquier otro
    // constructor con parametros, porque el compilador deja de generarlo gratis.
    public Carrera() {
    }

    // --- Mutators (setters) ---
    // Metodo publico, no devuelve nada (void), recibe un parametro del mismo
    // tipo que el atributo. Aqui el parametro se llama igual que el atributo
    // (idCarrera), asi que el "this" es obligatorio para distinguir uno de otro:
    // this.idCarrera es el atributo de este objeto; idCarrera (sin this) es el
    // parametro que acaba de llegar.
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    // --- Accessors (getters) ---
    // Publico, sin parametros, devuelve el valor del atributo.
    public int getIdCarrera() {
        return this.idCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getNombreCarrera() {
        return this.nombreCarrera;
    }

    public void setDescripcionCarrera(String descripcionCarrera) {
        this.descripcionCarrera = descripcionCarrera;
    }

    public String getDescripcionCarrera() {
        return this.descripcionCarrera;
    }

    // --- toString ---
    // Este metodo ya existe en toda clase de Java (lo hereda de Object);
    // aqui se sobrescribe (@Override) para que imprima algo entendible en
    // vez del texto por defecto. Se arma con StringBuilder, agregando pedazo
    // por pedazo con append() en vez de concatenar con "+".
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(getIdCarrera()).append("\n");
        sb.append("nombre: ").append(getNombreCarrera()).append("\n");
        sb.append("descripcion: ").append(getDescripcionCarrera()).append("\n");
        return sb.toString();
    }
}
