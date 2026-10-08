package com.academico.domain.exception;

/**
 * Se lanza cuando se viola una regla del negocio (correo repetido,
 * créditos fuera de rango, matrícula duplicada, etc.).
 * El mensaje está pensado para mostrarse directamente al usuario.
 */
public class DominioException extends RuntimeException {

    public DominioException(String mensaje) {
        super(mensaje);
    }
}
