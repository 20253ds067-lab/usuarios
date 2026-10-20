package mx.edu.utez.proyecto1c.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCrearUsuarioDTO {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El username debe estar entre 4 y 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número"
    )
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
            regexp = "^(ADMIN|GESTOR|ESTUDIANTE)$",
            message = "El rol debe ser ADMIN, GESTOR o ESTUDIANTE"
    )
    private String rol;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;
    private String segundoApellido;
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    @NotBlank (message = "El correo es obligatorio")
    @Email(message = "El correo es invalido")
    private String correo;
    private String curp;
    private String descripcion;


}
