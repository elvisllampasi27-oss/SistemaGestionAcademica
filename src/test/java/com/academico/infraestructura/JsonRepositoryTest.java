package com.academico.infraestructura;

import com.academico.domain.exception.PersistenciaException;
import com.academico.domain.model.Curso;
import com.academico.domain.model.Estudiante;
import com.academico.infraestructura.persistence.CursoRepositoryJson;
import com.academico.infraestructura.persistence.EstudianteRepositoryJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonRepositoryTest {

    @TempDir
    Path carpeta;

    @Test
    void sinArchivoDevuelveListaVacia() {
        var repo = new EstudianteRepositoryJson(carpeta.resolve("no-existe.json"));
        assertTrue(repo.listar().isEmpty());
    }

    @Test
    void guardaYRecuperaConTildes() {
        var repo = new CursoRepositoryJson(carpeta.resolve("datos").resolve("cursos.json"));
        repo.guardar(List.of(new Curso(1, "Programación Orientada a Objetos", 4)));

        List<Curso> leidos = repo.listar();
        assertEquals(1, leidos.size());
        assertEquals("Programación Orientada a Objetos", leidos.get(0).getNombre());
        assertEquals(4, leidos.get(0).getCreditos());
    }

    @Test
    void laListaDevueltaEsModificable() {
        var repo = new EstudianteRepositoryJson(carpeta.resolve("e.json"));
        repo.guardar(List.of(new Estudiante(1, "Ana Quispe", "ana@correo.com")));
        List<Estudiante> leidos = repo.listar();
        assertDoesNotThrow(() -> leidos.add(new Estudiante(2, "Luis Mamani", "luis@correo.com")));
    }

    @Test
    void unArchivoDañadoLanzaExcepcionEnVezDeSerIgnorado() throws IOException {
        Path archivo = carpeta.resolve("roto.json");
        Files.writeString(archivo, "{ esto no es una lista json");
        var repo = new EstudianteRepositoryJson(archivo);
        assertThrows(PersistenciaException.class, repo::listar);
    }
}
