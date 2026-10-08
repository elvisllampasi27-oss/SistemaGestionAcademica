package com.academico.infraestructura.persistence;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;

import java.nio.file.Path;

public class EstudianteRepositoryJson extends JsonRepository<Estudiante>
        implements EstudianteRepository {

    public EstudianteRepositoryJson() {
        this(Path.of("data", "estudiantes.json"));
    }

    // Constructor con ruta: permite usar un archivo temporal en las pruebas.
    public EstudianteRepositoryJson(Path archivo) {
        super(archivo, Estudiante.class);
    }
}
