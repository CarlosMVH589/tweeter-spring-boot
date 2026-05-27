package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;

public class InstrumentoResponseDTO {
  private Long id;
  private String instrumento;
  private String imagenUrl;
  private UserResponseDTO postedBy;

  public InstrumentoResponseDTO(Instrumento instrumento) {
    this.id = instrumento.getId();
    this.instrumento = instrumento.getInstrumento();
    this.imagenUrl = instrumento.getImagenUrl();
    this.postedBy = new UserResponseDTO(instrumento.getPostedBy());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public UserResponseDTO getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(UserResponseDTO postedBy) {
    this.postedBy = postedBy;
  }
}
