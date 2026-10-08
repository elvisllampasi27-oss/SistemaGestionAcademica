package com.academico.domain.model;

import java.time.LocalDate;

/** Relación entre un estudiante y un curso en el que está inscrito. */
public class Matricula implements Identificable {

    private final int id;
    private final int estudianteId;
    private final int cursoId;
    // Se guarda como texto ISO (2026-10-07) para que el JSON sea simple y legible.
    private final String fecha;

    public Matricula(int id, int estudianteId, int cursoId) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.fecha = LocalDate.now().toString();
    }

    @Override
    public int getId() {
        return id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public String getFecha() {
        return fecha;
    }
}
