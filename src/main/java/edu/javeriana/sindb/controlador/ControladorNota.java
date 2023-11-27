package edu.javeriana.sindb.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.javeriana.sindb.ex.NotasInvalidas;
import edu.javeriana.sindb.modelo.Nota;
import edu.javeriana.sindb.repositorio.RepositorioNota;
import edu.javeriana.sindb.service.NotaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
public class ControladorNota {

    private final NotaService notaServiceFacade;

    @Autowired
    private RepositorioNota repositorioNota;

    @Autowired
    public ControladorNota(NotaService notaServiceFacade) {
        this.notaServiceFacade = notaServiceFacade;
    }

    @GetMapping("/notas")
    List<Nota> traeNotas() {
        return repositorioNota.findAll();
    }

    @GetMapping("/nota-estudiante/{estudianteId}")
    public List<Nota> traeNotasPorEstudiante(@PathVariable Integer estudianteId) {
        return repositorioNota.findByEstudiante_id(estudianteId);
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
    public ResponseEntity<?> creaNota(@RequestBody Nota nota) {
        try {
            Nota notaGuardada = notaServiceFacade.guardarNota(nota);
            return ResponseEntity.ok(notaGuardada);
        } catch (NotasInvalidas e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ExceptionHandler(NotasInvalidas.class)
    public ResponseEntity<String> handleNotasInvalidasException(NotasInvalidas e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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