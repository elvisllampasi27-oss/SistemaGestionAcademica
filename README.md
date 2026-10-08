# Sistema de Gestión Académica

Aplicación de consola en Java 17 para administrar estudiantes, cursos y matrículas.
Proyecto del Laboratorio 03: **Arquitectura Limpia**.

## Cómo ejecutarlo

```bash
mvn test                    # corre las pruebas unitarias
mvn compile exec:java       # inicia el programa
```

Los datos se guardan en la carpeta `data/` (JSON), que se crea sola al registrar el primer dato.

## Arquitectura

```
presentation  ──►  application  ──►  domain  ◄──  infraestructura
 (menús)          (casos de uso)    (reglas)      (archivos JSON)
```

La regla es que las dependencias apuntan hacia el dominio. El dominio no conoce
Gson, ni archivos, ni la consola.

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Dominio | `domain.model` | `Estudiante`, `Curso`, `Matricula` y sus validaciones |
| Dominio | `domain.repository` | Interfaces que describen cómo guardar y leer |
| Aplicación | `application` | Casos de uso: registrar, buscar, matricular, etc. |
| Infraestructura | `infraestructura.persistence` | Implementación con archivos JSON (Gson) |
| Presentación | `presentation` | Menús por consola |

`Main` es la única clase que conoce las implementaciones concretas y las conecta.

## Reglas de negocio

- El nombre del estudiante tiene al menos 3 caracteres y el correo es válido y único.
- El nombre del curso es único y sus créditos están entre 1 y 6.
- Un estudiante no puede matricularse dos veces en el mismo curso.
- Un estudiante no puede pasar de **22 créditos** (`MatriculaService.MAX_CREDITOS`).
- No se elimina un estudiante con matrículas ni un curso con estudiantes.
- Los IDs se generan automáticamente.

## Decisiones de diseño

- **Entidades inmutables.** Para actualizar se crea un objeto nuevo; así nunca existe uno con datos inválidos.
- **Un solo `JsonRepository<T>`.** Evita repetir el mismo código de lectura y escritura por cada entidad.
- **Los errores de lectura no se ocultan.** Si el JSON está dañado se lanza `PersistenciaException`; devolver una lista vacía haría que el siguiente guardado borrara los datos.
- **Pruebas sin disco.** Los servicios dependen de interfaces, por eso los tests usan repositorios en memoria.

## Limitaciones y mejoras posibles

- Se reescribe el archivo completo en cada cambio; con muchos datos convendría una base de datos (bastaría con otra implementación de `Repositorio`).
- No hay notas ni periodos académicos.
- No hay control de concurrencia (es de un solo usuario).
