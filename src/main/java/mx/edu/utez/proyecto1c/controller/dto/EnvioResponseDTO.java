package mx.edu.utez.proyecto1c.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvioResponseDTO {
    private double subtotal;
    private double recargos;
    private double total;
    private String mensaje;
}
