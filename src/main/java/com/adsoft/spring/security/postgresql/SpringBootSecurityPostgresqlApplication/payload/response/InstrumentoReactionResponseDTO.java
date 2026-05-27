package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.InstrumentoReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;

public class InstrumentoReactionResponseDTO {
  private Long id;
  private Long instrumentoId;
  private Long reactionId;
  private Long userId;
  private String reactionDescription;

  public InstrumentoReactionResponseDTO(InstrumentoReaction instrumentoReaction) {
    this.id = instrumentoReaction.getId();
    this.instrumentoId = instrumentoReaction.getInstrumentoId();
    this.userId = instrumentoReaction.getUserId();

    Reaction reaction = instrumentoReaction.getReaction();
    if (reaction != null) {
      this.reactionDescription = reaction.getDescription() != null ? reaction.getDescription().name() : null;
      this.reactionId = reactionCode(reaction.getDescription());
    }
  }

  private Long reactionCode(EReaction description) {
    if (description == null) {
      return null;
    }

    return switch (description) {
      case REACTION_LIKE -> 1L;
      case REACTION_LOVE -> 2L;
      case REACTION_HATE -> 3L;
      case REACTION_SAD -> 4L;
      case REACTION_ANGRY -> 5L;
    };
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getReactionDescription() {
    return reactionDescription;
  }

  public void setReactionDescription(String reactionDescription) {
    this.reactionDescription = reactionDescription;
  }
}