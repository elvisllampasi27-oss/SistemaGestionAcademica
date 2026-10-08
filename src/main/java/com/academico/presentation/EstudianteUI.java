package com.academico.presentation;

import com.academico.application.EstudianteService;
import com.academico.domain.model.Estudiante;

import java.util.List;

public class EstudianteUI {

    private final EstudianteService service;
    private final Consola consola;

    public EstudianteUI(EstudianteService service, Consola consola) {
        this.service = service;
        this.consola = consola;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- ESTUDIANTES ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por nombre o correo");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = consola.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> consola.ejecutar(this::registrar);
                case 2 -> consola.ejecutar(() -> imprimir(service.listar()));
                case 3 -> consola.ejecutar(this::buscar);
                case 4 -> consola.ejecutar(this::actualizar);
                case 5 -> consola.ejecutar(this::eliminar);
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        String nombre = consola.leerTexto("Nombre: ");
        String correo = consola.leerTexto("Correo: ");
        Estudiante creado = service.registrar(nombre, correo);
        System.out.println("Estudiante registrado con ID " + creado.getId() + ".");
    }

    private void buscar() {
        String texto = consola.leerTexto("Texto a buscar: ");
        imprimir(service.buscar(texto));
    }

    private void actualizar() {
        int id = consola.leerEntero("ID del estudiante: ");
        Estudiante actual = service.obtener(id);
        System.out.println("Datos actuales: " + actual);
        String nombre = consola.leerTexto("Nuevo nombre: ");
        String correo = consola.leerTexto("Nuevo correo: ");
        service.actualizar(id, nombre, correo);
        System.out.println("Estudiante actualizado.");
    }

    private void eliminar() {
        int id = consola.leerEntero("ID a eliminar: ");
        service.eliminar(id);
        System.out.println("Estudiante eliminado.");
    }

    private void imprimir(List<Estudiante> estudiantes) {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para mostrar.");
            return;
        }
        System.out.printf("%-5s %-28s %s%n", "ID", "NOMBRE", "CORREO");
        estudiantes.forEach(e ->
                System.out.printf("%-5d %-28s %s%n", e.getId(), e.getNombre(), e.getCorreo()));
    }
}
