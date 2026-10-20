package mx.edu.utez.proyecto1c.model.repository.usuario;


import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository
        extends JpaRepository<Usuario ,Long> {


    //select * from usuarios where username=?
    Optional <Usuario> findByUsername(String username);


}
