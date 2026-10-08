package com.academico.infraestructura.persistence;

import com.academico.domain.model.Curso;
import com.academico.domain.repository.CursoRepository;

import java.nio.file.Path;

public class CursoRepositoryJson extends JsonRepository<Curso> implements CursoRepository {

    public CursoRepositoryJson() {
        this(Path.of("data", "cursos.json"));
    }

    public CursoRepositoryJson(Path archivo) {
        super(archivo, Curso.class);
    }
}
