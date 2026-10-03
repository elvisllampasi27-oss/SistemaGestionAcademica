package com.academico;

import com.academico.application.CursoService;
import com.academico.application.EstudianteService;
import com.academico.domain.repository.CursoRepository;
import com.academico.domain.repository.EstudianteRepository;
import com.academico.infraestructura.persistence.CursoRepositoryJson;
import com.academico.infraestructura.persistence.EstudianteRepositoryJson;
import com.academico.presentation.CursoUI;
import com.academico.presentation.EstudianteUI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ===== Infraestructura =====
        EstudianteRepository estudianteRepo = new EstudianteRepositoryJson();
        CursoRepository cursoRepo = new CursoRepositoryJson();

        // ===== Aplicación =====
        EstudianteService estudianteService = new EstudianteService(estudianteRepo);
        CursoService cursoService = new CursoService(cursoRepo);

        // ===== Presentación =====
        EstudianteUI estudianteUI = new EstudianteUI(estudianteService);
        CursoUI cursoUI = new CursoUI(cursoService);

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN ACADÉMICA ===");
            System.out.println("1. Gestionar estudiantes");
            System.out.println("2. Gestionar cursos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> estudianteUI.mostrarMenu(sc);
                case 2 -> cursoUI.mostrarMenu(sc);
                case 0 -> System.out.println("Sistema finalizado.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}