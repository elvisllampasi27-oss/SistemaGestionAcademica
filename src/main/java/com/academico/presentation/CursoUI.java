package com.academico.presentation;

import com.academico.application.CursoService;
import com.academico.domain.model.Curso;

import java.util.List;

public class CursoUI {

    private final CursoService service;
    private final Consola consola;

    public CursoUI(CursoService service, Consola consola) {
        this.service = service;
        this.consola = consola;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- CURSOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            opcion = consola.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> consola.ejecutar(this::registrar);
                case 2 -> consola.ejecutar(() -> imprimir(service.listar()));
                case 3 -> consola.ejecutar(this::actualizar);
                case 4 -> consola.ejecutar(this::eliminar);
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        String nombre = consola.leerTexto("Nombre del curso: ");
        int creditos = consola.leerEntero(
                "Créditos (" + Curso.MIN_CREDITOS + "-" + Curso.MAX_CREDITOS + "): ");
        Curso creado = service.registrar(nombre, creditos);
        System.out.println("Curso registrado con ID " + creado.getId() + ".");
    }

    private void actualizar() {
        int id = consola.leerEntero("ID del curso: ");
        Curso actual = service.obtener(id);
        System.out.println("Datos actuales: " + actual);
        String nombre = consola.leerTexto("Nuevo nombre: ");
        int creditos = consola.leerEntero("Nuevos créditos: ");
        service.actualizar(id, nombre, creditos);
        System.out.println("Curso actualizado.");
    }

    private void eliminar() {
        int id = consola.leerEntero("ID a eliminar: ");
        service.eliminar(id);
        System.out.println("Curso eliminado.");
    }

    private void imprimir(List<Curso> cursos) {
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }
        System.out.printf("%-5s %-32s %s%n", "ID", "CURSO", "CRÉDITOS");
        cursos.forEach(c ->
                System.out.printf("%-5d %-32s %d%n", c.getId(), c.getNombre(), c.getCreditos()));
    }
}
