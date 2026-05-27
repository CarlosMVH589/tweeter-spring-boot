package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models;

import java.util.HashSet;
import java.util.Set;

// AGREGAMOS ESTA IMPORTACIÓN PARA EL JSON
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "instrumentos")
public class Instrumento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 140)
  private String instrumento;
  
  // SOLUCIÓN CRÍTICA: Forzamos la lectura del JSON y la escritura en Postgres
  @JsonProperty("imagenUrl")
  @Column(name = "imagen_url")
  private String imagenUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "posted_by", referencedColumnName = "id")
  private User postedBy;

  @OneToMany(mappedBy = "instrumento")
  private Set<InstrumentoReaction> likes = new HashSet<>();

  // Constructores
  public Instrumento() {
  }

  public Instrumento(String instrumento) {
    this.instrumento = instrumento;
  }

  public Instrumento(String instrumento, String imagenUrl) {
    this.instrumento = instrumento;
    this.imagenUrl = imagenUrl;
  }

  // Getters y Setters
  public User getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(User postedBy) {
    this.postedBy = postedBy;
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

  public Set<InstrumentoReaction> getLikes() {
    return likes;
  }

  public void setLikes(Set<InstrumentoReaction> likes) {
    this.likes = likes;
  }
}