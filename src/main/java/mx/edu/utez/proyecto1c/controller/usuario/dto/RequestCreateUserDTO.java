package mx.edu.utez.proyecto1c.controller.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateUserDTO {
    @NotBlank (message = "El username es obligatorio")
    @Size(min = 6, max = 20, message = "El usernme debe contener entre 6 a 20 caracteres")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número")
    @NotBlank (message = "Password es obligatorio")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(ADMIN|PROFESOR|ESTUDIANTE)$",
            message = "El rol debe ser ADMIN, PROFESOR o ESTUDIANTE")
    private String rol;

    private String descripcion;
}
