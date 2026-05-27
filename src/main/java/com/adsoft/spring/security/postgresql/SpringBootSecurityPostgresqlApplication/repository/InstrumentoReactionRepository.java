package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.InstrumentoReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;

@Repository
public interface InstrumentoReactionRepository extends JpaRepository<InstrumentoReaction, Long> {

    Optional<InstrumentoReaction> findByUserAndInstrumento(User user, Instrumento instrumento);

}

