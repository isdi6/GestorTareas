package es.ies.javafx;

/*
Vamos a desarrollar una pequeña aplicación de escritorio denominada Gestor de tareas.

La aplicación permitirá al usuario:
    - Escribir una nueva tarea.
    - Asignarle una prioridad: baja, media o alta.
    - Añadirla a una lista.
    - Marcar una tarea como completada.
    - Eliminar una tarea seleccionada.
    - Eliminar todas las tareas completadas previa selección/limpieza.
    - Consultar cuántas tareas están pendientes, completadas y en total.

Durante la actividad trabajaremos con algunos de los componentes más habituales de JavaFX:
Label, TextField, ComboBox, Button, ListView, HBox, VBox.
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestorTareas extends Application {

    private ComboBox<String> prioridad;
    private ListView<String> lista;
    private Button botonesCompletado;
    private Button botonesEliminado;
    private Button botonesLimpiar;
    private Label mensaje;
    private Label estado;
    private TextField campoTarea;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ETIQUETA DE TÍTULO
        Label titulo = new Label("Gestor tareas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Caja de texto para la nueva tarea
        campoTarea = new TextField();
        campoTarea.setPromptText("Escriba una tarea");
        HBox.setHgrow(campoTarea, Priority.ALWAYS); 

        // Desplegable para la prioridad
        prioridad = new ComboBox<>();
        prioridad.getItems().addAll("Baja", "Media", "Alta");
        prioridad.setValue("Media");

        // Botón para añadir tarea
        Button aniadir = new Button("Añadir tarea");
        aniadir.setDefaultButton(true);

        // Contenedor del formulario superior
        HBox formulario = new HBox(10, new Label("Nueva Tarea"), campoTarea, new Label("Prioridad:"), prioridad, aniadir);
        formulario.setAlignment(Pos.CENTER_LEFT);

        // Lista de tareas (ListView)
        lista = new ListView<>();
        lista.setPrefHeight(280);

        // Botones de acciones inferiores
        botonesCompletado = new Button("Marcar como completada");
        botonesEliminado = new Button("Eliminar");
        botonesLimpiar = new Button("Limpiar completadas");

        // Ordenamos los botones horizontalmente
        HBox acciones = new HBox(10, botonesCompletado, botonesEliminado, botonesLimpiar);
        acciones.setAlignment(Pos.CENTER_LEFT);

        // Etiquetas para estado e información al usuario
        mensaje = new Label();
        mensaje.setWrapText(true);
        estado = new Label();

        // Estructura principal en vertical (VBox)
        VBox raiz = new VBox(14, titulo, formulario, new Label("Lista de tareas"), lista, acciones, mensaje, estado);
        raiz.setPadding(new Insets(20));
        VBox.setVgrow(lista, Priority.ALWAYS);

        // Asignación de eventos a los botones
        aniadir.setOnAction(e -> aniadirTarea());
        botonesCompletado.setOnAction(e -> completarTarea());
        botonesEliminado.setOnAction(e -> eliminarTarea());
        botonesLimpiar.setOnAction(e -> limpiarCompletadas());

        // Inicializamos la etiqueta de estado de las tareas


        // Configuración de la escena y ventana principal
        Scene scene = new Scene(raiz, 820, 500);
        primaryStage.setTitle("Gestor de tareas en JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para añadir una tarea a la lista
    private void aniadirTarea() {
        String texto = campoTarea.getText().trim();

        if (texto.isEmpty()) {
            mostrarMensaje("Introduzca una tarea. Tarea vacía.");
            return;
        }

        String prio = prioridad.getValue();
        String tarea = "[" + prio + "] " + texto;

        lista.getItems().add(tarea);
        lista.getSelectionModel().selectLast();
        lista.scrollTo(lista.getItems().size() - 1);

        // Restablecer valores por defecto
        campoTarea.clear();
        prioridad.setValue("Media");
        mostrarMensaje("¡Tarea añadida correctamente!");

        campoTarea.requestFocus();
    }

    // Método para marcar como completada la tarea seleccionada
    private void completarTarea() {
        int index = lista.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            mostrarMensaje("Seleccione una tarea para marcarla como completada.");
            return;
        }

        String tarea = lista.getItems().get(index);

        if (tarea.startsWith("[COMPLETADA]")) {
            mostrarMensaje("La tarea seleccionada ya está completada.");
            return;
        }

        // Modificamos el texto de la tarea agregando la marca de completada
        lista.getItems().set(index, "[COMPLETADA] " + tarea);
        mostrarMensaje("Tarea marcada como completada.");

    }

    // Método para eliminar la tarea seleccionada
    private void eliminarTarea() {
        int index = lista.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            mostrarMensaje("Seleccione una tarea para eliminar.");
            return;
        }

        lista.getItems().remove(index);
        mostrarMensaje("Tarea eliminada correctamente.");

    }

    // Método para limpiar todas las tareas que estén completadas
    private void limpiarCompletadas() {
        // Filtramos y eliminamos los elementos que empiecen por [COMPLETADA]
        boolean eliminadas = lista.getItems().removeIf(tarea -> tarea.startsWith("[COMPLETADA]"));

        if (eliminadas) {
            mostrarMensaje("Se han limpiado las tareas completadas.");
        } else {
            mostrarMensaje("No hay tareas completadas para limpiar.");
        }

    }

    // Actualiza la etiqueta de resumen con el conteo de tareas
    

    // Muestra un mensaje en pantalla
    private void mostrarMensaje(String text) {
        this.mensaje.setText(text);
    }
}