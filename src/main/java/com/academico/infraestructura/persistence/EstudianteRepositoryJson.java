package com.academico.infraestructura.persistence;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepositoryJson implements EstudianteRepository {

    private final String archivo = "data/estudiantes.json";
    private final Gson gson = new Gson();

    @Override
    public List<Estudiante> listar() {
        File file = new File(archivo);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type tipo = new TypeToken<List<Estudiante>>() {}.getType();
            List<Estudiante> estudiantes = gson.fromJson(reader, tipo);
            return estudiantes != null ? estudiantes : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void guardar(List<Estudiante> estudiantes) {
        File file = new File(archivo);
        file.getParentFile().mkdirs();

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(estudiantes, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar Estudiante: " + e.getMessage());
        }
    }
}