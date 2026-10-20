package mx.edu.utez.proyecto1c.service.usuario;

import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.repository.persona.PersonaRepository;
import mx.edu.utez.proyecto1c.model.repository.usuario.UsuarioRepository;
import mx.edu.utez.proyecto1c.model.usuario.Roles;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));
    }

    @Transactional
    public Usuario createUser(RequestCrearUsuarioDTO payload) {
        //Validar si el username existe
        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new BadRequestException("Ese usuario ya esta registrado");
        }

        if (payload.getCorreo() != null && personaRepository.findByCorreo(payload.getCorreo()).isPresent()) {
            throw new BadRequestException("El correo ya se encuentra registrado");
        }

        Persona newPersona = new Persona();
        newPersona.setNombres(payload.getNombres());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setFechaNacimiento(payload.getFechaNacimiento());
        newPersona.setCorreo(payload.getCorreo());
        newPersona.setCurp(payload.getCurp());

        newPersona = this.personaRepository.save(newPersona);

        //Crear instancia
        Usuario newUser = new Usuario();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(payload.getPassword());
        newUser.setDescripcion(payload.getDescripcion());
        newUser.setEnable(true);
        newUser.setRol(Roles.valueOf(payload.getRol()));
        newUser.setPersona(newPersona);

        return usuarioRepository.save(newUser);
    }

    @Transactional
    public Usuario activarUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));

        usuario.setEnable(true);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario desactivarUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));

        usuario.setEnable(false);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario cambiarEstadoUsuario(Long id, Boolean estado) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));

        if (estado != null) {
            usuario.setEnable(estado);
        } else {
            usuario.setEnable(!usuario.isEnable());
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarUsuario(Long id, RequestActualizarUsuarioDTO payload) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El id proporcionado es inválido");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));

        if (payload.getUsername() != null && !payload.getUsername().equals(usuario.getUsername())) {
            Optional<Usuario> usuarioConMismoUsername = usuarioRepository.findByUsername(payload.getUsername());
            if (usuarioConMismoUsername.isPresent() && !usuarioConMismoUsername.get().getId().equals(id)) {
                throw new BadRequestException("El username ya se encuentra registrado por otro usuario");
            }
            usuario.setUsername(payload.getUsername());
        }

        if (payload.getDescripcion() != null) {
            usuario.setDescripcion(payload.getDescripcion());
        }

        return usuarioRepository.save(usuario);
    }
}
