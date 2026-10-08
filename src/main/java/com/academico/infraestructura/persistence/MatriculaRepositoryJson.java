package com.academico.infraestructura.persistence;

import com.academico.domain.model.Matricula;
import com.academico.domain.repository.MatriculaRepository;

import java.nio.file.Path;

public class MatriculaRepositoryJson extends JsonRepository<Matricula>
        implements MatriculaRepository {

    public MatriculaRepositoryJson() {
        this(Path.of("data", "matriculas.json"));
    }

    public MatriculaRepositoryJson(Path archivo) {
        super(archivo, Matricula.class);
    }
}
