package edu.javeriana.sindb.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.javeriana.sindb.modelo.Estudiante;
import edu.javeriana.sindb.repositorio.RepositorioEstudiante;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
public class ControladorEstudiante {

    @Autowired
    RepositorioEstudiante repositorioEstudiante;

    @GetMapping("/estudiantes")
    List<Estudiante> traeEstudiantes() {
        return repositorioEstudiante.findAll();
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<Estudiante> traeEstudiantePorId(@PathVariable Integer id) {
        Optional<Estudiante> registroEstudiante = repositorioEstudiante.findById(id);

        if (registroEstudiante.isPresent()) {
            return new ResponseEntity<>(registroEstudiante.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/crea")
    public Estudiante creaEstudiante(@RequestBody Estudiante estudiante) {
        return repositorioEstudiante.save(estudiante);
    }

    @PutMapping("/act/{id}")
    public ResponseEntity<Estudiante> actualizaEstudiante(@PathVariable Integer id,
            @RequestBody Estudiante estudiante) {
        Optional<Estudiante> registroEstudiante = repositorioEstudiante.findById(id);

        if (registroEstudiante.isPresent()) {
            Estudiante nuevoEstudiante = registroEstudiante.get();
            nuevoEstudiante.setNombre(estudiante.getNombre());
            nuevoEstudiante.setApellido(estudiante.getApellido());
            nuevoEstudiante.setCorreo(estudiante.getCorreo());

            return new ResponseEntity<>(repositorioEstudiante.save(nuevoEstudiante), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borra/{id}")
    public ResponseEntity<HttpStatus> deleteEstudiante(@PathVariable Integer id) {
        try {
            repositorioEstudiante.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
