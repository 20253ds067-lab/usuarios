package mx.edu.utez.proyecto1c.service;


import mx.edu.utez.proyecto1c.controller.dto.EnvioRequestDTO;
import mx.edu.utez.proyecto1c.controller.dto.EnvioResponseDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.PaqueteRechazadoException;
import org.springframework.stereotype.Service;

@Service
public class EnvioService {

    public EnvioResponseDTO calcularEnvio(EnvioRequestDTO dto) {
        double volumen = dto.getLargo() * dto.getAncho() * dto.getAlto(); //[cite: 1]


        if (dto.getPeso() > 50) {
            throw new PaqueteRechazadoException("El paquete fue rechazado: el peso supera los 50 kg.");
        }
        if (dto.getLargo() > 150 || dto.getAncho() > 150 || dto.getAlto() > 150) {
            throw new PaqueteRechazadoException("El paquete fue rechazado: alguna de sus dimensiones supera los 150 cm.");
        }
        if (volumen > 1000000) {
            throw new PaqueteRechazadoException("El paquete fue rechazado: el volumen supera los 1,000,000 cm³.");
        }

        double subtotal = 80.0;
        subtotal += dto.getPeso() * 12.0;

        if (volumen > 50000) {
            subtotal += 100.0;
        }

        double recargos = 0.0;

        if (dto.getTipoServicio() != null) {
            if ("EXPRESS".equalsIgnoreCase(dto.getTipoServicio())) {
                recargos += subtotal * 0.40;
            } else if ("MISMO_DIA".equalsIgnoreCase(dto.getTipoServicio())) {
                recargos += subtotal * 0.70;
            }
        }

        if (dto.getValorDeclarado() > 10000) {
            recargos += dto.getValorDeclarado() * 0.02;
        }

        double total = subtotal + recargos;

        return new EnvioResponseDTO(subtotal, recargos, total, "Cálculo realizado con éxito");
    }
}