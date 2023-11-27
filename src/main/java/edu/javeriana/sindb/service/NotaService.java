package edu.javeriana.sindb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.javeriana.sindb.ex.NotasInvalidas;
import edu.javeriana.sindb.modelo.Nota;
import edu.javeriana.sindb.repositorio.RepositorioNota;

@Service
public class NotaService {

    private final RepositorioNota repositorioNota;

    @Autowired
    public NotaService(RepositorioNota repositorioNota) {
        this.repositorioNota = repositorioNota;
    }

    public Nota guardarNota(Nota nota) {
        validarNotasEstudiante(nota.getEstudiante_id(), nota.getPorcentaje());
        return repositorioNota.save(nota);
    }

    private void validarNotasEstudiante(Integer estudianteId, Double porcentajeNuevaNota) {
        // Lógica para obtener las notas existentes del estudiante y validar el
        // porcentaje acumulado
        List<Nota> notasEstudiante = repositorioNota.findByEstudiante_id(estudianteId);

        double porcentajeAcumulado = notasEstudiante.stream()
                .mapToDouble(Nota::getPorcentaje)
                .sum();

        if (porcentajeAcumulado + porcentajeNuevaNota > 100.0) {
            throw new NotasInvalidas("La suma de los porcentajes de las notas supera el 100%");
        }
    }
}
