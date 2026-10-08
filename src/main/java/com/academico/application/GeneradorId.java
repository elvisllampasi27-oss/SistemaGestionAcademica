package com.academico.application;

import com.academico.domain.model.Identificable;

import java.util.List;

/** Calcula el siguiente ID libre, para que el usuario no tenga que inventarlo. */
final class GeneradorId {

    private GeneradorId() {
    }

    static int siguiente(List<? extends Identificable> existentes) {
        return existentes.stream()
                .mapToInt(Identificable::getId)
                .max()
                .orElse(0) + 1;
    }
}
