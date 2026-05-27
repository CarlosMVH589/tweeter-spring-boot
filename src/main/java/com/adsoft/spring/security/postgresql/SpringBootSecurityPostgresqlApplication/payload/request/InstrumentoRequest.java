package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InstrumentoRequest {

    @NotBlank
    @Size(max = 140)
    private String instrumento;

    private String imagenUrl; // Jackson leerá esto directamente como "imagenUrl"

    // Getters y Setters
    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}