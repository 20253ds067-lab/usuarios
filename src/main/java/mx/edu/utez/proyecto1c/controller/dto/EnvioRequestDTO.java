package mx.edu.utez.proyecto1c.controller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvioRequestDTO {

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;

    @NotNull(message = "El largo es obligatorio")
    @Positive(message = "El largo debe ser mayor a 0")
    private Double largo;

    @NotNull(message = "El ancho es obligatorio")
    @Positive(message = "El ancho debe ser mayor a 0")
    private Double ancho;

    @NotNull(message = "El alto es obligatorio")
    @Positive(message = "El alto debe ser mayor a 0")
    private Double alto;

    private String tipoServicio;

    @Min(value = 0, message = "El valor declarado no puede ser negativo")
    private double valorDeclarado;
}