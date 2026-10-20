package mx.edu.utez.proyecto1c.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.dto.RequestBodyDTO;
import mx.edu.utez.proyecto1c.controller.dto.RequestCalculadoraDTO;
import mx.edu.utez.proyecto1c.service.EnvioService;
import mx.edu.utez.proyecto1c.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.edu.utez.proyecto1c.controller.dto.EnvioRequestDTO;
import mx.edu.utez.proyecto1c.controller.dto.EnvioResponseDTO;

@RestController
@CrossOrigin({"*"})// permitimos todos los origenes de peticiones
@RequestMapping("/my-service")
public class MyController {

    private final MyService service;
    private final EnvioService envio;

    //inyeccion de dependencias
    public MyController (MyService service,EnvioService envio) {
        this.service = service;
        this.envio = envio;
    }

    @GetMapping("/my-service")
    public String miPrimerServicio() {
        return "Hello word";
    }

    @GetMapping("/servicio2")
    public String servicio2() {
        return "segundo servicio";
    }

    @PostMapping
    public String servico3() {
        return "este es el servicio 3";
    }

    @GetMapping("/path/{id}")
    public String pathvariable(@PathVariable String id) {
        return "el path variable es " + id;
    }
    @PostMapping("/body")
    public ResponseEntity<RequestBodyDTO> body(@RequestBody @Valid RequestBodyDTO payload){
        System.out.println(payload.getEdad());
        System.out.println(payload.getNombre());

        return ResponseEntity
                .status(201)
                .body(payload);
    }
    @GetMapping("/fizzbuzz/{n}")
    public String fizzBuzz(@PathVariable int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
        return "Luis Felipe Jimenez Quintero 4C";
    }

    @GetMapping("/fibonacci/{n}")
    public String fibonacci(@PathVariable int n) {
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            System.out.println(a);
            long siguiente = a + b;
            a = b;
            b = siguiente;
        }
        return "Luis Felipe Jimenez Quintero 4C";
    }
    @PostMapping("/calculadora")
    public double calculadora(@RequestBody @Valid RequestCalculadoraDTO payload){
        return service.calculadora(payload);
    }
    @PostMapping("/envio")
    public ResponseEntity<EnvioResponseDTO> calcularEnvio(@RequestBody @Valid EnvioRequestDTO payload) {
        EnvioResponseDTO respuesta = envio.calcularEnvio(payload);
        return ResponseEntity.ok(respuesta);
    }
}


