package mx.edu.utez.proyecto1c.model.repository.persona;

import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
    Optional<Persona> findByCorreo(String correo);
    Optional<Persona> findByCurp(String curp);

}
