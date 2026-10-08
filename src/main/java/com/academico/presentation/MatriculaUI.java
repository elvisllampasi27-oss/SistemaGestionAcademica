package com.academico.presentation;

import com.academico.application.EstudianteService;
import com.academico.application.MatriculaService;
import com.academico.domain.model.Curso;
import com.academico.domain.model.Estudiante;

import java.util.List;

public class MatriculaUI {

    private final MatriculaService service;
    private final EstudianteService estudianteService;
    private final Consola consola;

    public MatriculaUI(MatriculaService service, EstudianteService estudianteService, Consola consola) {
        this.service = service;
        this.estudianteService = estudianteService;
        this.consola = consola;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MATRÍCULAS ---");
            System.out.println("1. Matricular estudiante en un curso");
            System.out.println("2. Cancelar matrícula");
            System.out.println("3. Ver cursos de un estudiante");
            System.out.println("4. Ver estudiantes de un curso");
            System.out.println("0. Volver");
            opcion = consola.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> consola.ejecutar(this::matricular);
                case 2 -> consola.ejecutar(this::cancelar);
                case 3 -> consola.ejecutar(this::verCursosDeEstudiante);
                case 4 -> consola.ejecutar(this::verEstudiantesDeCurso);
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void matricular() {
        int estudianteId = consola.leerEntero("ID del estudiante: ");
        int cursoId = consola.leerEntero("ID del curso: ");
        service.matricular(estudianteId, cursoId);
        System.out.println("Matrícula realizada. Créditos actuales: "
                + service.creditosDe(estudianteId) + "/" + MatriculaService.MAX_CREDITOS);
    }

    private void cancelar() {
        int estudianteId = consola.leerEntero("ID del estudiante: ");
        int cursoId = consola.leerEntero("ID del curso: ");
        service.cancelar(estudianteId, cursoId);
        System.out.println("Matrícula cancelada.");
    }

    private void verCursosDeEstudiante() {
        int id = consola.leerEntero("ID del estudiante: ");
        Estudiante estudiante = estudianteService.obtener(id);
        List<Curso> cursos = service.cursosDe(id);

        System.out.println("Estudiante: " + estudiante.getNombre());
        if (cursos.isEmpty()) {
            System.out.println("No está matriculado en ningún curso.");
            return;
        }
        cursos.forEach(c -> System.out.println("  - " + c));
        System.out.println("Total: " + service.creditosDe(id) + " créditos.");
    }

    private void verEstudiantesDeCurso() {
        int id = consola.leerEntero("ID del curso: ");
        List<Estudiante> estudiantes = service.estudiantesDe(id);
        if (estudiantes.isEmpty()) {
            System.out.println("Nadie está matriculado en ese curso.");
            return;
        }
        estudiantes.forEach(e -> System.out.println("  - " + e));
    }
}
