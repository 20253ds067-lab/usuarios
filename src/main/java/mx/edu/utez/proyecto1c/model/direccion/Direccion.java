package mx.edu.utez.proyecto1c.model.direccion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.proyecto1c.model.persona.Persona;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private String colonia;
    private String municipio;
    private String estado;
    private int numero;
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
