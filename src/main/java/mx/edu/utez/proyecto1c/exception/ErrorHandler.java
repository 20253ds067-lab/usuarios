package mx.edu.utez.proyecto1c.exception;

import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.exception.customExceptions.PaqueteRechazadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> manejarBadRequest(
            BadRequestException ex) {

        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(PaqueteRechazadoException.class)
    public ResponseEntity<Map<String, String>> manejarPaqueteRechazado(PaqueteRechazadoException ex) {
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("estatus", "RECHAZADO");
        respuesta.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El parámetro '" + ex.getName() + "' tiene un formato inválido");
        return ResponseEntity.badRequest().body(errores);
    }
}