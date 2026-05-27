package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.InstrumentoResponseDTO;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.MessageResponse;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.InstrumentoRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<Page<InstrumentoResponseDTO>> getAllInstrumentos(Pageable pageable) {
        Page<InstrumentoResponseDTO> page = instrumentoRepository.findAll(pageable)
                .map(InstrumentoResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createInstrumento(@Valid @RequestBody com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request.InstrumentoRequest instrumentoRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username: " + username));

        // Aquí extraemos los datos limpios del DTO sin interferencias de Hibernate
        Instrumento myInstrumento = new Instrumento(
                instrumentoRequest.getInstrumento(),
                instrumentoRequest.getImagenUrl()
        );
        myInstrumento.setPostedBy(user);
        
        instrumentoRepository.save(myInstrumento);
        return ResponseEntity.ok(new MessageResponse("Instrumento guardado con éxito."));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()") // MODIFICADO: Permite borrar a cualquier usuario autenticado sin importar su rol
    public ResponseEntity<?> deleteInstrumento(@PathVariable Long id) {
        if (!instrumentoRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El instrumento no existe."));
        }
        instrumentoRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Instrumento eliminado con éxito."));
    }
}