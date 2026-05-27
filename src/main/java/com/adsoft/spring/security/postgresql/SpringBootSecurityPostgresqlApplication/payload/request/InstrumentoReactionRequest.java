package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InstrumentoReactionRequest {

  @NotNull
  private Long instrumentoId;

  @NotNull
  @Min(1)
  @Max(5)
  private Long reactionId;

  public Long getInstrumentoId() {
    return instrumentoId;
  }

  public void setInstrumentoId(Long instrumentoId) {
    this.instrumentoId = instrumentoId;
  }

  public Long getReactionId() {
    return reactionId;
  }

  public void setReactionId(Long reactionId) {
    this.reactionId = reactionId;
  }

}