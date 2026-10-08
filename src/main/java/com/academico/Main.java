package com.academico;

import com.academico.application.CursoService;
import com.academico.application.EstudianteService;
import com.academico.application.MatriculaService;
import com.academico.infraestructura.persistence.CursoRepositoryJson;
import com.academico.infraestructura.persistence.EstudianteRepositoryJson;
import com.academico.infraestructura.persistence.MatriculaRepositoryJson;
import com.academico.presentation.Consola;
import com.academico.presentation.CursoUI;
import com.academico.presentation.EstudianteUI;
import com.academico.presentation.MatriculaUI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Este es el único lugar que conoce las clases concretas (JSON).
        // Todo lo demás depende de interfaces.
        var estudianteRepo = new EstudianteRepositoryJson();
        var cursoRepo = new CursoRepositoryJson();
        var matriculaRepo = new MatriculaRepositoryJson();

        var estudianteService = new EstudianteService(estudianteRepo, matriculaRepo);
        var cursoService = new CursoService(cursoRepo, matriculaRepo);
        var matriculaService = new MatriculaService(matriculaRepo, estudianteRepo, cursoRepo);

        try (Scanner scanner = new Scanner(System.in)) {
            Consola consola = new Consola(scanner);
            var estudianteUI = new EstudianteUI(estudianteService, consola);
            var cursoUI = new CursoUI(cursoService, consola);
            var matriculaUI = new MatriculaUI(matriculaService, estudianteService, consola);

            int opcion;
            do {
                System.out.println("\n=== SISTEMA DE GESTIÓN ACADÉMICA ===");
                System.out.println("1. Estudiantes");
                System.out.println("2. Cursos");
                System.out.println("3. Matrículas");
                System.out.println("0. Salir");
                opcion = consola.leerEntero("Seleccione una opción: ");

                switch (opcion) {
                    case 1 -> estudianteUI.mostrarMenu();
                    case 2 -> cursoUI.mostrarMenu();
                    case 3 -> matriculaUI.mostrarMenu();
                    case 0 -> System.out.println("Hasta pronto.");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);
        }
    }
}
