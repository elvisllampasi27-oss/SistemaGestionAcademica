package com.academico.domain.model;

import com.academico.domain.exception.DominioException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Estudiante implements Identificable {

    private static final Pattern FORMATO_CORREO =
            Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final int LARGO_MINIMO_NOMBRE = 3;

    // Los campos son finales: para "modificar" un estudiante se crea uno nuevo.
    // Así es imposible que quede un objeto con datos inválidos.
    private final int id;
    private final String nombre;
    private final String correo;

    public Estudiante(int id, String nombre, String correo) {
        if (id <= 0) {
            throw new DominioException("El ID debe ser un número positivo.");
        }
        if (nombre == null || nombre.trim().length() < LARGO_MINIMO_NOMBRE) {
            throw new DominioException(
                    "El nombre debe tener al menos " + LARGO_MINIMO_NOMBRE + " caracteres.");
        }
        if (correo == null || !FORMATO_CORREO.matcher(correo.trim()).matches()) {
            throw new DominioException("El correo no tiene un formato válido.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.correo = correo.trim().toLowerCase();
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Estudiante otro && id == otro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " (" + correo + ")";
    }
}
