// VentanaCarrera — la interfaz grafica de escritorio del ejercicio de la
// Clase 4: "interfaz grafica de usuario con botoncitos para Windows...
// aplicacion de escritorio para darle soporte a la carrera".
//
// Es puro Swing (viene incluido en el JDK, sin dependencias externas) y
// reusa CarreraDAO y Carrera tal cual — la ventana no sabe nada de SQL,
// solo llama a los 5 metodos del DAO.
//
// El flujo de botones sigue exactamente lo que describio el profesor:
//   - Al cargar, solo "Nuevo" esta habilitado.
//   - "Nuevo": oculta/deshabilita el id (nunca se escribe a mano), habilita
//     nombre y descripcion, habilita "Guardar".
//   - Clic en una fila de la tabla: selecciona ese registro, llena el
//     formulario (incluido el id, de solo lectura) y habilita "Actualizar"
//     y "Eliminar". Es el mismo mecanismo que explico con el ejemplo de
//     Pedro Picapiedra: el id viaja pegado a la fila que tocaste.
//   - "Guardar" / "Actualizar" / "Eliminar": llaman al DAO, refrescan la
//     tabla y regresan al estado inicial.
//   - "Cancelar" (el que el profesor dejo como opcional: "¿le quieren poner
//     un Cancelar? Adelante"): regresa al estado inicial sin guardar nada.

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class VentanaCarrera extends JFrame {

    private final CarreraDAO dao = new CarreraDAO();

    // --- formulario ---
    private final JTextField campoId = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoDescripcion = new JTextField();

    // --- botones ---
    private final JButton btnNuevo = new JButton("Nuevo");
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnListar = new JButton("Listar");
    private final JButton btnCancelar = new JButton("Cancelar");

    // --- tabla ---
    private final ModeloTablaCarrera modeloTabla = new ModeloTablaCarrera();
    private final JTable tabla = new JTable(modeloTabla);

    public VentanaCarrera() {
        super("Carrera");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));

        add(construirFormulario(), BorderLayout.NORTH);
        add(construirTabla(), BorderLayout.CENTER);

        registrarEventos();
        estadoInicial();
        cargarTabla();

        setSize(560, 480);
        setLocationRelativeTo(null);
    }

    // ---------------------------------------------------------------
    // construccion de la interfaz
    // ---------------------------------------------------------------

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new BorderLayout(14, 0));

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Carrera");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 17f));
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        campos.add(titulo, gc);
        gc.gridwidth = 1;

        agregarFila(campos, gc, 1, "id Carrera", campoId);
        agregarFila(campos, gc, 2, "nombre", campoNombre);
        agregarFila(campos, gc, 3, "descripcion", campoDescripcion);

        // el id nunca lo escribe el usuario: llega solo o lo trae la fila
        // seleccionada. En vez de ocultar el campo (que complica el layout
        // para un primer ejercicio de Swing), se deja siempre visible pero
        // de solo lectura — cumple lo mismo: "no lo necesito para escribir".
        campoId.setEditable(false);

        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        for (JButton b : new JButton[]{btnNuevo, btnGuardar, btnActualizar, btnEliminar, btnListar, btnCancelar}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(140, 28));
            botones.add(b);
            botones.add(Box.createVerticalStrut(6));
        }

        panel.add(campos, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.EAST);
        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gc, int fila, String etiqueta, JTextField campo) {
        gc.gridx = 0;
        gc.gridy = fila;
        gc.weightx = 0;
        panel.add(new JLabel(etiqueta), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        campo.setColumns(18);
        panel.add(campo, gc);
    }

    private JScrollPane construirTabla() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
        return new JScrollPane(tabla);
    }

    private void registrarEventos() {
        btnNuevo.addActionListener(e -> modoNuevo());
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> cargarTabla());
        btnCancelar.addActionListener(e -> estadoInicial());
    }

    // ---------------------------------------------------------------
    // estados (esto es la parte que el profesor pidio explicitamente)
    // ---------------------------------------------------------------

    private void estadoInicial() {
        limpiarCampos();
        habilitarCampos(false);
        tabla.clearSelection();
        tabla.setEnabled(true);

        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    private void modoNuevo() {
        limpiarCampos();
        habilitarCampos(true);
        tabla.clearSelection();
        tabla.setEnabled(false); // no seleccionar filas mientras se crea una nueva

        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);

        campoNombre.requestFocusInWindow();
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        Carrera c = modeloTabla.getCarreraEn(fila);
        campoId.setText(String.valueOf(c.getIdCarrera()));
        campoNombre.setText(c.getNombreCarrera());
        campoDescripcion.setText(c.getDescripcionCarrera());
        habilitarCampos(true);

        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    // ---------------------------------------------------------------
    // acciones — cada una llama a un metodo del DAO y ya
    // ---------------------------------------------------------------

    private void guardar() {
        if (!validarCampos()) {
            return;
        }
        Carrera c = new Carrera();
        c.setNombreCarrera(campoNombre.getText().trim());
        c.setDescripcionCarrera(campoDescripcion.getText().trim());
        try {
            dao.create(c);
            cargarTabla();
            estadoInicial();
        } catch (SQLException e) {
            mostrarError("No se pudo guardar la carrera", e);
        }
    }

    private void actualizar() {
        if (!validarCampos()) {
            return;
        }
        Carrera c = new Carrera();
        c.setIdCarrera(Integer.parseInt(campoId.getText()));
        c.setNombreCarrera(campoNombre.getText().trim());
        c.setDescripcionCarrera(campoDescripcion.getText().trim());
        try {
            dao.update(c);
            cargarTabla();
            estadoInicial();
        } catch (SQLException e) {
            mostrarError("No se pudo actualizar la carrera", e);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        Carrera seleccionada = modeloTabla.getCarreraEn(fila);
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la carrera \"" + seleccionada.getNombreCarrera() + "\"?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            dao.delete(seleccionada);
            cargarTabla();
            estadoInicial();
        } catch (SQLException e) {
            mostrarError("No se pudo eliminar la carrera", e);
        }
    }

    private void cargarTabla() {
        try {
            List<Carrera> carreras = dao.readAll();
            modeloTabla.setDatos(carreras);
        } catch (SQLException e) {
            mostrarError("No se pudo leer el catalogo de carreras", e);
            modeloTabla.setDatos(Collections.emptyList());
        }
    }

    // ---------------------------------------------------------------
    // ayudantes
    // ---------------------------------------------------------------

    private boolean validarCampos() {
        if (campoNombre.getText().trim().isEmpty() || campoDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y descripcion son obligatorios.",
                "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        campoId.setText("");
        campoNombre.setText("");
        campoDescripcion.setText("");
    }

    private void habilitarCampos(boolean habilitar) {
        campoNombre.setEnabled(habilitar);
        campoDescripcion.setEnabled(habilitar);
        // campoId se queda siempre no-editable — ver el comentario de mas arriba
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(this, mensaje + ":\n" + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ---------------------------------------------------------------
    // modelo de la tabla — envuelve la List<Carrera> que devuelve el DAO
    // ---------------------------------------------------------------

    private static class ModeloTablaCarrera extends AbstractTableModel {
        private final String[] columnas = {"ID", "Nombre", "Descripcion"};
        private List<Carrera> datos = Collections.emptyList();

        void setDatos(List<Carrera> nuevosDatos) {
            this.datos = (nuevosDatos != null) ? nuevosDatos : Collections.emptyList();
            fireTableDataChanged();
        }

        Carrera getCarreraEn(int fila) {
            return datos.get(fila);
        }

        @Override
        public int getRowCount() {
            return datos.size();
        }

        @Override
        public int getColumnCount() {
            return columnas.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnas[col];
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false; // la tabla es de solo lectura: se edita por el formulario de arriba
        }

        @Override
        public Object getValueAt(int row, int col) {
            Carrera c = datos.get(row);
            switch (col) {
                case 0: return c.getIdCarrera();
                case 1: return c.getNombreCarrera();
                case 2: return c.getDescripcionCarrera();
                default: return null;
            }
        }
    }
}
