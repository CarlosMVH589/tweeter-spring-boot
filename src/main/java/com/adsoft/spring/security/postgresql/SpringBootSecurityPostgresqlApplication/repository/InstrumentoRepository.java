package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {

}

