package edu.javeriana.sindb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.javeriana.sindb.modelo.Nota;

public interface RepositorioNota extends JpaRepository<Nota, Integer> {


}
