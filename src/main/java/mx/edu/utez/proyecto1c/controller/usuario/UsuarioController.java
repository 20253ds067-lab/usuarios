package mx.edu.utez.proyecto1c.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import mx.edu.utez.proyecto1c.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin({"*"})
public class UsuarioController {

    //inyeccion de dependencias
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //exposicion de servicios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(this.usuarioService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody @Valid RequestCrearUsuarioDTO payload) {
        return ResponseEntity.status(201).body(this.usuarioService.createUser(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.getUserById(id));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Usuario> activarUsuarioPatch(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.activarUsuario(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Usuario> activarUsuarioPut(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.activarUsuario(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuarioPatch(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.desactivarUsuario(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuarioPut(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.desactivarUsuario(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstadoUsuarioPatch(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(this.usuarioService.cambiarEstadoUsuario(id, estado));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstadoUsuarioPut(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(this.usuarioService.cambiarEstadoUsuario(id, estado));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Usuario> cambiarStatusUsuarioPatch(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(this.usuarioService.cambiarEstadoUsuario(id, estado));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Usuario> cambiarStatusUsuarioPut(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(this.usuarioService.cambiarEstadoUsuario(id, estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuarioPut(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarUsuarioDTO payload) {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(id, payload));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuarioPatch(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarUsuarioDTO payload) {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(id, payload));
    }

    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuarioBody(
            @RequestBody @Valid RequestActualizarUsuarioDTO payload) {
        if (payload.getId() == null) {
            throw new BadRequestException("El id del usuario es obligatorio");
        }
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(payload.getId(), payload));
    }
}
