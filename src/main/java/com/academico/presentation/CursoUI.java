package com.academico.presentation;

import com.academico.application.CursoService;
import com.academico.domain.model.Curso;

import java.util.List;
import java.util.Scanner;

public class CursoUI {

    private final CursoService service;

    public CursoUI(CursoService service) {
        this.service = service;
    }

    public void mostrarMenu(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n--- CURSOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Regresar");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrar(sc);
                case 2 -> listar();
                case 3 -> actualizar(sc);
                case 4 -> eliminar(sc);
                case 0 -> System.out.println("Regresando...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrar(Scanner sc) {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Créditos: ");
        int creditos = sc.nextInt();
        sc.nextLine();

        service.registrar(new Curso(id, nombre, creditos));
        System.out.println("Curso registrado.");
    }

    private void listar() {
        List<Curso> cursos = service.listar();
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }
        cursos.forEach(c ->
                System.out.println(c.getId() + " - " + c.getNombre() + " - " + c.getCreditos() + " créditos")
        );
    }

    private void actualizar(Scanner sc) {
        System.out.print("ID del curso: ");
        int idAct = sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombreAct = sc.nextLine();

        System.out.print("Nuevos créditos: ");
        int creditosAct = sc.nextInt();
        sc.nextLine();

        boolean actualizado = service.actualizar(new Curso(idAct, nombreAct, creditosAct));
        System.out.println(actualizado ? "Curso actualizado." : "Curso no encontrado.");
    }

    private void eliminar(Scanner sc) {
        System.out.print("ID a eliminar: ");
        int idEliminar = sc.nextInt();
        sc.nextLine();

        boolean eliminado = service.eliminar(idEliminar);
        System.out.println(eliminado ? "Curso eliminado." : "Curso no encontrado.");
    }
}