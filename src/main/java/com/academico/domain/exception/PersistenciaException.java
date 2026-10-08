package com.academico.domain.exception;

/**
 * Error al leer o escribir los datos. Vive en el dominio porque las interfaces
 * de repositorio la declaran como parte de su contrato; así las capas de arriba
 * no dependen de ningún detalle de infraestructura (JSON, archivos, etc.).
 */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
