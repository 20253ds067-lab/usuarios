package mx.edu.utez.proyecto1c;

import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.controller.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.persona.PersonaController;
import mx.edu.utez.proyecto1c.controller.usuario.UsuarioController;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import mx.edu.utez.proyecto1c.service.persona.PersonaService;
import mx.edu.utez.proyecto1c.service.usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Transactional
class UsuarioPersonaServiceTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PersonaController personaController;

    private RequestCrearUsuarioDTO generarUsuarioDTO() {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        RequestCrearUsuarioDTO dto = new RequestCrearUsuarioDTO();
        dto.setUsername("user_" + randomSuffix);
        dto.setPassword("Secret1234");
        dto.setRol("ESTUDIANTE");
        dto.setNombres("Juan");
        dto.setPrimerApellido("Perez");
        dto.setSegundoApellido("Gomez");
        dto.setFechaNacimiento(new Date(1000000000000L));
        dto.setCorreo("juan_" + randomSuffix + "@correo.com");
        dto.setCurp("PEJU900101HDFRRN" + randomSuffix.substring(0, 2).toUpperCase());
        dto.setDescripcion("Usuario de prueba inicial");
        return dto;
    }

    @Test
    void testCrearYConsultarUsuarioPorId() {
        RequestCrearUsuarioDTO dto = generarUsuarioDTO();
        Usuario creado = usuarioService.createUser(dto);

        Assertions.assertNotNull(creado.getId());
        Assertions.assertNotNull(creado.getPersona());
        Assertions.assertEquals("Juan", creado.getPersona().getNombres());

        Usuario consultado = usuarioService.getUserById(creado.getId());
        Assertions.assertEquals(creado.getId(), consultado.getId());
        Assertions.assertEquals(dto.getUsername(), consultado.getUsername());
        Assertions.assertNotNull(consultado.getPersona());
        Assertions.assertEquals(creado.getPersona().getId(), consultado.getPersona().getId());

        ResponseEntity<Usuario> response = usuarioController.getUserById(creado.getId());
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testConsultarUsuarioInexistenteLanzaExcepcion() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            usuarioService.getUserById(999999L);
        });
    }

    @Test
    void testActivarYDesactivarUsuario() {
        RequestCrearUsuarioDTO dto = generarUsuarioDTO();
        Usuario creado = usuarioService.createUser(dto);

        Usuario desactivado = usuarioService.desactivarUsuario(creado.getId());
        Assertions.assertFalse(desactivado.isEnable());

        Usuario activado = usuarioService.activarUsuario(creado.getId());
        Assertions.assertTrue(activado.isEnable());

        ResponseEntity<Usuario> responseDesactivar = usuarioController.desactivarUsuarioPatch(creado.getId());
        Assertions.assertFalse(responseDesactivar.getBody().isEnable());

        ResponseEntity<Usuario> responseActivar = usuarioController.activarUsuarioPatch(creado.getId());
        Assertions.assertTrue(responseActivar.getBody().isEnable());
    }

    @Test
    void testActualizarUsernameYDescripcionPreservaRelacionPersona() {
        RequestCrearUsuarioDTO dto = generarUsuarioDTO();
        Usuario creado = usuarioService.createUser(dto);
        Long personaIdOriginal = creado.getPersona().getId();
        String passwordOriginal = creado.getPassword();

        String nuevoUsername = "upd_" + UUID.randomUUID().toString().substring(0, 8);
        RequestActualizarUsuarioDTO updateDto = new RequestActualizarUsuarioDTO();
        updateDto.setUsername(nuevoUsername);
        updateDto.setDescripcion("Descripcion completamente nueva y actualizada");

        Usuario actualizado = usuarioService.actualizarUsuario(creado.getId(), updateDto);

        Assertions.assertEquals(nuevoUsername, actualizado.getUsername());
        Assertions.assertEquals("Descripcion completamente nueva y actualizada", actualizado.getDescripcion());

        Assertions.assertNotNull(actualizado.getPersona());
        Assertions.assertEquals(personaIdOriginal, actualizado.getPersona().getId());

        Assertions.assertEquals(passwordOriginal, actualizado.getPassword());
        Assertions.assertTrue(actualizado.isEnable());
        Assertions.assertEquals(creado.getRol(), actualizado.getRol());

        updateDto.setDescripcion("Segunda descripcion actualizada por controller");
        ResponseEntity<Usuario> controllerResponse = usuarioController.actualizarUsuarioPut(creado.getId(), updateDto);
        Assertions.assertEquals(200, controllerResponse.getStatusCode().value());
        Assertions.assertEquals("Segunda descripcion actualizada por controller", controllerResponse.getBody().getDescripcion());
        Assertions.assertEquals(personaIdOriginal, controllerResponse.getBody().getPersona().getId());
    }

    @Test
    void testActualizarPersonaConServicioYControladorPropios() {
        RequestCrearUsuarioDTO dto = generarUsuarioDTO();
        Usuario creado = usuarioService.createUser(dto);
        Long personaId = creado.getPersona().getId();

        RequestActualizarPersonaDTO personaUpdateDto = new RequestActualizarPersonaDTO();
        personaUpdateDto.setNombres("Carlos Alberto");
        personaUpdateDto.setPrimerApellido("Ramirez");
        personaUpdateDto.setSegundoApellido("Sanchez");
        personaUpdateDto.setCorreo("carlos.nuevo@" + UUID.randomUUID().toString().substring(0, 6) + ".com");
        personaUpdateDto.setCurp("RASC950505HDFRRN01");
        personaUpdateDto.setFechaNacimiento(new Date(950000000000L));

        Persona personaActualizada = personaService.updatePersona(personaId, personaUpdateDto);

        Assertions.assertEquals("Carlos Alberto", personaActualizada.getNombres());
        Assertions.assertEquals("Ramirez", personaActualizada.getPrimerApellido());
        Assertions.assertEquals("Sanchez", personaActualizada.getSegundoApellido());
        Assertions.assertEquals(personaUpdateDto.getCorreo(), personaActualizada.getCorreo());
        Assertions.assertEquals("RASC950505HDFRRN01", personaActualizada.getCurp());

        personaUpdateDto.setNombres("Carlos Modificado");
        ResponseEntity<Persona> response = personaController.updatePersona(personaId, personaUpdateDto);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals("Carlos Modificado", response.getBody().getNombres());
    }

    @Test
    void testValidacionPersonaInexistente() {
        RequestActualizarPersonaDTO personaUpdateDto = new RequestActualizarPersonaDTO();
        personaUpdateDto.setNombres("Inexistente");
        personaUpdateDto.setPrimerApellido("Test");
        personaUpdateDto.setCorreo("test@correo.com");

        Assertions.assertThrows(BadRequestException.class, () -> {
            personaService.updatePersona(999999L, personaUpdateDto);
        });
    }
}
