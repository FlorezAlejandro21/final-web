package edu.javeriana.sindb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.javeriana.sindb.modelo.Estudiante;

public interface RepositorioEstudiante extends JpaRepository<Estudiante, Integer> {

    
}
