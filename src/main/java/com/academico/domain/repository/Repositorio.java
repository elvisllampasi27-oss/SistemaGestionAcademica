package com.academico.domain.repository;

import com.academico.domain.exception.PersistenciaException;

import java.util.List;

/**
 * Contrato común de persistencia. Se guarda la lista completa porque el
 * volumen de datos de este sistema es pequeño; si creciera, bastaría con
 * cambiar la implementación (por ejemplo a una base de datos) sin tocar
 * los casos de uso.
 */
public interface Repositorio<T> {

    /** Devuelve una lista nueva y modificable; vacía si aún no hay datos. */
    List<T> listar() throws PersistenciaException;

    void guardar(List<T> elementos) throws PersistenciaException;
}
