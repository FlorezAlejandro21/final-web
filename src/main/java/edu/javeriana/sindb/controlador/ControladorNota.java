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

import edu.javeriana.sindb.modelo.Nota;
import edu.javeriana.sindb.repositorio.RepositorioNota;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
public class ControladorNota {

    @Autowired
    private RepositorioNota repositorioNota;

    @GetMapping("/notas")
    List<Nota> traeNotas() {
        return repositorioNota.findAll();
    }

    @GetMapping("/nota/{id}")
    public ResponseEntity<Nota> traeNotaPorId(@PathVariable Integer id) {
        Optional<Nota> registroNota = repositorioNota.findById(id);

        if (registroNota.isPresent()) {
            return new ResponseEntity<>(registroNota.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/nota-crea")
    public Nota creaEstudiante(@RequestBody Nota nota) {
        return repositorioNota.save(nota);
    }

    @PutMapping("/nota-act/{id}")
    public ResponseEntity<Nota> actualizaEstudiante(@PathVariable Integer id,
            @RequestBody Nota nota) {
        Optional<Nota> registroNota = repositorioNota.findById(id);

        if (registroNota.isPresent()) {
            Nota nuevaNota = registroNota.get();
            nuevaNota.setEstudiante_id(nota.getEstudiante_id());
            nuevaNota.setObservacion(nota.getObservacion());
            nuevaNota.setValor(nuevaNota.getValor());
            nuevaNota.setPorcentaje(nuevaNota.getPorcentaje());

            return new ResponseEntity<>(repositorioNota.save(nuevaNota), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/nota-borra/{id}")
    public ResponseEntity<HttpStatus> deleteEstudiante(@PathVariable Integer id) {
        try {
            repositorioNota.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}