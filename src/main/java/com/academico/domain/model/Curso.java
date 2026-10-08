package com.academico.domain.model;

import com.academico.domain.exception.DominioException;

import java.util.Objects;

public class Curso implements Identificable {

    public static final int MIN_CREDITOS = 1;
    public static final int MAX_CREDITOS = 6;

    private final int id;
    private final String nombre;
    private final int creditos;

    public Curso(int id, String nombre, int creditos) {
        if (id <= 0) {
            throw new DominioException("El ID debe ser un número positivo.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new DominioException("El nombre del curso no puede estar vacío.");
        }
        if (creditos < MIN_CREDITOS || creditos > MAX_CREDITOS) {
            throw new DominioException(String.format(
                    "Los créditos deben estar entre %d y %d.", MIN_CREDITOS, MAX_CREDITOS));
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.creditos = creditos;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Curso otro && id == otro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " (" + creditos + " créditos)";
    }
}
