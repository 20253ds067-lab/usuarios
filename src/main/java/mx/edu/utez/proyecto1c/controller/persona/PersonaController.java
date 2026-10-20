package mx.edu.utez.proyecto1c.controller.persona;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
@CrossOrigin({"*"})
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(this.personaService.getAllPersonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        return ResponseEntity.ok(this.personaService.getPersonaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        return ResponseEntity.ok(this.personaService.updatePersona(id, payload));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Persona> patchPersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        return ResponseEntity.ok(this.personaService.updatePersona(id, payload));
    }

    @PutMapping
    public ResponseEntity<Persona> updatePersonaBody(
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        if (payload.getId() == null) {
            throw new BadRequestException("El id de la persona es obligatorio");
        }
        return ResponseEntity.ok(this.personaService.updatePersona(payload.getId(), payload));
    }
}
