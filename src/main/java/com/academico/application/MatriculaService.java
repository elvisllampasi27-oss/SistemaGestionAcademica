package com.academico.application;

import com.academico.domain.exception.DominioException;
import com.academico.domain.model.Curso;
import com.academico.domain.model.Estudiante;
import com.academico.domain.model.Matricula;
import com.academico.domain.repository.CursoRepository;
import com.academico.domain.repository.EstudianteRepository;
import com.academico.domain.repository.MatriculaRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MatriculaService {

    /** Tope de créditos que un estudiante puede llevar a la vez. */
    public static final int MAX_CREDITOS = 22;

    private final MatriculaRepository repository;
    private final EstudianteRepository estudiantes;
    private final CursoRepository cursos;

    public MatriculaService(MatriculaRepository repository,
                            EstudianteRepository estudiantes,
                            CursoRepository cursos) {
        this.repository = repository;
        this.estudiantes = estudiantes;
        this.cursos = cursos;
    }

    public Matricula matricular(int estudianteId, int cursoId) {
        exigirEstudiante(estudianteId);
        Curso curso = exigirCurso(cursoId);

        List<Matricula> todas = repository.listar();
        if (existe(todas, estudianteId, cursoId)) {
            throw new DominioException("El estudiante ya está matriculado en ese curso.");
        }

        int actuales = creditosDe(estudianteId);
        if (actuales + curso.getCreditos() > MAX_CREDITOS) {
            throw new DominioException(String.format(
                    "Superaría el máximo de %d créditos (lleva %d y el curso tiene %d).",
                    MAX_CREDITOS, actuales, curso.getCreditos()));
        }

        Matricula nueva = new Matricula(GeneradorId.siguiente(todas), estudianteId, cursoId);
        todas.add(nueva);
        repository.guardar(todas);
        return nueva;
    }

    public void cancelar(int estudianteId, int cursoId) {
        List<Matricula> todas = repository.listar();
        boolean quitada = todas.removeIf(m ->
                m.getEstudianteId() == estudianteId && m.getCursoId() == cursoId);
        if (!quitada) {
            throw new DominioException("Ese estudiante no está matriculado en ese curso.");
        }
        repository.guardar(todas);
    }

    public List<Curso> cursosDe(int estudianteId) {
        exigirEstudiante(estudianteId);
        Set<Integer> idsCursos = repository.listar().stream()
                .filter(m -> m.getEstudianteId() == estudianteId)
                .map(Matricula::getCursoId)
                .collect(Collectors.toSet());
        return cursos.listar().stream()
                .filter(c -> idsCursos.contains(c.getId()))
                .toList();
    }

    public List<Estudiante> estudiantesDe(int cursoId) {
        exigirCurso(cursoId);
        Set<Integer> idsEstudiantes = repository.listar().stream()
                .filter(m -> m.getCursoId() == cursoId)
                .map(Matricula::getEstudianteId)
                .collect(Collectors.toSet());
        return estudiantes.listar().stream()
                .filter(e -> idsEstudiantes.contains(e.getId()))
                .toList();
    }

    public int creditosDe(int estudianteId) {
        return cursosDe(estudianteId).stream().mapToInt(Curso::getCreditos).sum();
    }

    private boolean existe(List<Matricula> lista, int estudianteId, int cursoId) {
        return lista.stream().anyMatch(m ->
                m.getEstudianteId() == estudianteId && m.getCursoId() == cursoId);
    }

    private void exigirEstudiante(int id) {
        boolean existe = estudiantes.listar().stream().anyMatch(e -> e.getId() == id);
        if (!existe) {
            throw new DominioException("No existe un estudiante con ID " + id + ".");
        }
    }

    private Curso exigirCurso(int id) {
        return cursos.listar().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DominioException("No existe un curso con ID " + id + "."));
    }
}
