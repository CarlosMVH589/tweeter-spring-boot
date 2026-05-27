package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models;
import jakarta.persistence.*;

@Entity
@Table( name = "instrumento_reactions",
          uniqueConstraints = { 
          @UniqueConstraint(columnNames = {"user_id", "instrumento_id"}
          ),
      
        }
)

public class InstrumentoReaction {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
 
   @Column(name = "reaction_id")
   Long reactionId;

   public Long getReactionId() {
    return reactionId;
}

   public void setReactionId(Long reactionId) {
    this.reactionId = reactionId;
   }

   @Column(name = "user_id")
   Long userId;

    public Long getUserId() {
    return userId;
}

   public void setUserId(Long userId) {
    this.userId = userId;
   }

    @Column(name = "instrumento_id")
    Long instrumentoId;

  public Long getInstrumentoId() {
        return instrumentoId;
    }

    public void setInstrumentoId(Long instrumentoId) {
        this.instrumentoId = instrumentoId;
    }

  public Long getId() {
    return id;
}

   public void setId(Long id) {
    this.id = id;
   }

  
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    @ManyToOne
    @MapsId("instrumentoId")
    @JoinColumn(name = "instrumento_id")
    Instrumento instrumento;

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumentoId = instrumento.getId();
        this.instrumento = instrumento;
    }

    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id")
    Reaction reaction;

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reactionId = reaction.getId();
        this.reaction = reaction;
    }

}
