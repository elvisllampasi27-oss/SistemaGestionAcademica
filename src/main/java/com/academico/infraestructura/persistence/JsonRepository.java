package com.academico.infraestructura.persistence;

import com.academico.domain.exception.PersistenciaException;
import com.academico.domain.repository.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda una lista de objetos en un archivo JSON.
 * Las tres entidades usaban el mismo código copiado; aquí está una sola vez
 * y cada repositorio concreto solo indica su archivo y su tipo.
 */
public abstract class JsonRepository<T> implements Repositorio<T> {

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected JsonRepository(Path archivo, Class<T> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }

    @Override
    public List<T> listar() {
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }
        // UTF-8 explícito: con FileReader se usa el charset del sistema y las tildes se rompen.
        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<T> datos = gson.fromJson(lector, tipoLista);
            return datos != null ? new ArrayList<>(datos) : new ArrayList<>();
        } catch (IOException | JsonParseException e) {
            // No se devuelve una lista vacía: si el archivo está dañado, el
            // siguiente guardado lo sobrescribiría y se perderían los datos.
            throw new PersistenciaException("No se pudo leer " + archivo + ".", e);
        }
    }

    @Override
    public void guardar(List<T> elementos) {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            Files.createDirectories(carpeta);
            try (Writer escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                gson.toJson(elementos, tipoLista, escritor);
            }
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo guardar " + archivo + ".", e);
        }
    }
}
