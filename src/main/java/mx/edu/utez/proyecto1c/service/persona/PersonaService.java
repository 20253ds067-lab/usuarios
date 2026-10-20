package mx.edu.utez.proyecto1c.service.persona;

import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public Persona getPersonaById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        return personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("La persona con id " + id + " no existe"));
    }

    @Transactional
    public Persona updatePersona(Long id, RequestActualizarPersonaDTO payload) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("La persona con id " + id + " no existe"));

        if (payload.getCorreo() != null && !payload.getCorreo().equalsIgnoreCase(persona.getCorreo())) {
            Optional<Persona> personaConMismoCorreo = personaRepository.findByCorreo(payload.getCorreo());
            if (personaConMismoCorreo.isPresent() && !personaConMismoCorreo.get().getId().equals(id)) {
                throw new BadRequestException("El correo ya se encuentra registrado por otra persona");
            }
            persona.setCorreo(payload.getCorreo());
        }

        if (payload.getCurp() != null && !payload.getCurp().isBlank() && !payload.getCurp().equalsIgnoreCase(persona.getCurp())) {
            Optional<Persona> personaConMismoCurp = personaRepository.findByCurp(payload.getCurp());
            if (personaConMismoCurp.isPresent() && !personaConMismoCurp.get().getId().equals(id)) {
                throw new BadRequestException("El CURP ya se encuentra registrado por otra persona");
            }
            persona.setCurp(payload.getCurp());
        }

        if (payload.getNombres() != null && !payload.getNombres().isBlank()) {
            persona.setNombres(payload.getNombres());
        }
        if (payload.getPrimerApellido() != null && !payload.getPrimerApellido().isBlank()) {
            persona.setPrimerApellido(payload.getPrimerApellido());
        }
        if (payload.getSegundoApellido() != null) {
            persona.setSegundoApellido(payload.getSegundoApellido());
        }
        if (payload.getFechaNacimiento() != null) {
            persona.setFechaNacimiento(payload.getFechaNacimiento());
        }

        return personaRepository.save(persona);
    }
}
