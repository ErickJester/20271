// Punto de entrada de la practica. Analogo al main() de Principal.java de
// las clases 3-4, solo que en vez de comentar y descomentar llamadas al
// DAO, aqui las dispara la interfaz grafica.
//
// SwingUtilities.invokeLater() es la forma correcta de arrancar cualquier
// ventana Swing: se asegura de que la interfaz se construya y se muestre
// en el Event Dispatch Thread (el unico hilo con el que Swing es seguro
// trabajar), en vez de hacerlo en el hilo main() directamente.

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaCarrera().setVisible(true));
    }
}
