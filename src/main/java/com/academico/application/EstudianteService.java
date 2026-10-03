package com.academico.application;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;

import java.util.List;

public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public void registrar(Estudiante estudiante) {
        List<Estudiante> estudiantes = repository.listar();
        estudiantes.add(estudiante);
        repository.guardar(estudiantes);
    }

    public List<Estudiante> listar() {
        return repository.listar();
    }

    public boolean actualizar(Estudiante estudiante) {
        List<Estudiante> estudiantes = repository.listar();
        for (Estudiante e : estudiantes) {
            if (e.getId() == estudiante.getId()) {
                e.setNombre(estudiante.getNombre());
                e.setCorreo(estudiante.getCorreo());
                repository.guardar(estudiantes);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int id) {
        List<Estudiante> estudiantes = repository.listar();
        boolean eliminado = estudiantes.removeIf(e -> e.getId() == id);
        if (eliminado) {
            repository.guardar(estudiantes);
        }
        return eliminado;
    }
}