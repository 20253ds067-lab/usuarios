package mx.edu.utez.proyecto1c.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCalculadoraDTO {
    private double num1;
    private double num2;

    @NotBlank(message = "La operacion es obligatoria")
    private String operacion;
}
