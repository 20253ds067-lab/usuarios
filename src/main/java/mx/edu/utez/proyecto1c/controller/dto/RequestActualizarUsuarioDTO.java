package mx.edu.utez.proyecto1c.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestActualizarUsuarioDTO {

    private Long id;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 50, message = "El username debe estar entre 4 y 50 caracteres")
    private String username;

    private String descripcion;
}
