package com.academico.application;

import com.academico.domain.exception.DominioException;
import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;
import com.academico.domain.repository.MatriculaRepository;

import java.util.List;

public class EstudianteService {

    private final EstudianteRepository repository;
    private final MatriculaRepository matriculas;

    public EstudianteService(EstudianteRepository repository, MatriculaRepository matriculas) {
        this.repository = repository;
        this.matriculas = matriculas;
    }

    public Estudiante registrar(String nombre, String correo) {
        List<Estudiante> todos = repository.listar();
        // Se construye primero: así el correo ya viene normalizado (minúsculas, sin espacios).
        Estudiante nuevo = new Estudiante(GeneradorId.siguiente(todos), nombre, correo);
        validarCorreoLibre(todos, nuevo);
        todos.add(nuevo);
        repository.guardar(todos);
        return nuevo;
    }

    public List<Estudiante> listar() {
        return repository.listar();
    }

    public Estudiante obtener(int id) {
        return repository.listar().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DominioException("No existe un estudiante con ID " + id + "."));
    }

    /** Busca por coincidencia parcial en nombre o correo, sin distinguir mayúsculas. */
    public List<Estudiante> buscar(String texto) {
        String filtro = texto.trim().toLowerCase();
        return repository.listar().stream()
                .filter(e -> e.getNombre().toLowerCase().contains(filtro)
                        || e.getCorreo().contains(filtro))
                .toList();
    }

    public Estudiante actualizar(int id, String nombre, String correo) {
        List<Estudiante> todos = repository.listar();
        int posicion = posicionDe(todos, id);
        Estudiante actualizado = new Estudiante(id, nombre, correo);
        validarCorreoLibre(todos, actualizado);
        todos.set(posicion, actualizado);
        repository.guardar(todos);
        return actualizado;
    }

    public void eliminar(int id) {
        boolean tieneMatriculas = matriculas.listar().stream()
                .anyMatch(m -> m.getEstudianteId() == id);
        if (tieneMatriculas) {
            throw new DominioException(
                    "No se puede eliminar: el estudiante tiene cursos matriculados.");
        }
        List<Estudiante> todos = repository.listar();
        todos.remove(posicionDe(todos, id));
        repository.guardar(todos);
    }

    private int posicionDe(List<Estudiante> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        throw new DominioException("No existe un estudiante con ID " + id + ".");
    }

    private void validarCorreoLibre(List<Estudiante> existentes, Estudiante candidato) {
        boolean repetido = existentes.stream().anyMatch(e ->
                e.getId() != candidato.getId() && e.getCorreo().equals(candidato.getCorreo()));
        if (repetido) {
            throw new DominioException(
                    "Ya hay un estudiante registrado con el correo " + candidato.getCorreo() + ".");
        }
    }
}
