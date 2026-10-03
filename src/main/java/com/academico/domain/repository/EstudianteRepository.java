package com.academico.domain.repository;

import com.academico.domain.model.Estudiante;
import java.util.List;

public interface EstudianteRepository {
    List<Estudiante> listar();
    void guardar(List<Estudiante> estudiantes);
}