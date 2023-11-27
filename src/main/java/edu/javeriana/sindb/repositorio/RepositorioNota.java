package edu.javeriana.sindb.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.javeriana.sindb.modelo.Nota;

public interface RepositorioNota extends JpaRepository<Nota, Integer> {
    
    @Query("SELECT n FROM Nota n WHERE n.estudiante_id = :estudianteId")
    List<Nota> findByEstudiante_id(@Param("estudianteId") Integer estudianteId);
}
