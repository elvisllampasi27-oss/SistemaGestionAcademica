package com.academico.application;

import com.academico.domain.exception.DominioException;
import com.academico.domain.model.Curso;
import com.academico.domain.repository.CursoRepository;
import com.academico.domain.repository.MatriculaRepository;

import java.util.List;

public class CursoService {

    private final CursoRepository repository;
    private final MatriculaRepository matriculas;

    public CursoService(CursoRepository repository, MatriculaRepository matriculas) {
        this.repository = repository;
        this.matriculas = matriculas;
    }

    public Curso registrar(String nombre, int creditos) {
        List<Curso> todos = repository.listar();
        Curso nuevo = new Curso(GeneradorId.siguiente(todos), nombre, creditos);
        validarNombreLibre(todos, nuevo);
        todos.add(nuevo);
        repository.guardar(todos);
        return nuevo;
    }

    public List<Curso> listar() {
        return repository.listar();
    }

    public Curso obtener(int id) {
        return repository.listar().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DominioException("No existe un curso con ID " + id + "."));
    }

    public Curso actualizar(int id, String nombre, int creditos) {
        List<Curso> todos = repository.listar();
        int posicion = posicionDe(todos, id);
        Curso actualizado = new Curso(id, nombre, creditos);
        validarNombreLibre(todos, actualizado);
        todos.set(posicion, actualizado);
        repository.guardar(todos);
        return actualizado;
    }

    public void eliminar(int id) {
        boolean tieneAlumnos = matriculas.listar().stream()
                .anyMatch(m -> m.getCursoId() == id);
        if (tieneAlumnos) {
            throw new DominioException("No se puede eliminar: el curso tiene estudiantes matriculados.");
        }
        List<Curso> todos = repository.listar();
        todos.remove(posicionDe(todos, id));
        repository.guardar(todos);
    }

    private int posicionDe(List<Curso> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        throw new DominioException("No existe un curso con ID " + id + ".");
    }

    private void validarNombreLibre(List<Curso> existentes, Curso candidato) {
        boolean repetido = existentes.stream().anyMatch(c ->
                c.getId() != candidato.getId()
                        && c.getNombre().equalsIgnoreCase(candidato.getNombre()));
        if (repetido) {
            throw new DominioException("Ya existe un curso llamado \"" + candidato.getNombre() + "\".");
        }
    }
}
