package com.academico.presentation;

import com.academico.domain.exception.DominioException;
import com.academico.domain.exception.PersistenciaException;

import java.util.Scanner;

/**
 * Entrada y salida por consola. Centraliza dos cosas que antes estaban
 * repetidas en cada menú: leer números sin que el programa se caiga si el
 * usuario escribe letras, y mostrar los errores del sistema de forma amable.
 */
public class Consola {

    private final Scanner scanner;

    public Consola(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("  Debe ingresar un número entero.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /** Ejecuta una acción del menú mostrando los errores esperables sin cortar el programa. */
    public void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (DominioException e) {
            System.out.println("  [!] " + e.getMessage());
        } catch (PersistenciaException e) {
            System.out.println("  [Error de almacenamiento] " + e.getMessage());
        }
    }
}
