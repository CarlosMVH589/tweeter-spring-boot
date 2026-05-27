package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Instrumento;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.InstrumentoReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request.InstrumentoReactionRequest;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.InstrumentoReactionResponseDTO;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.MessageResponse;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.InstrumentoReactionRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.InstrumentoRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.ReactionRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")
public class InstrumentoReactionController {

    @Autowired
    private InstrumentoReactionRepository instrumentoReactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @GetMapping("/all")
    public List<InstrumentoReactionResponseDTO> getInstrumento() {
        return instrumentoReactionRepository.findAll()
                .stream()
                .map(InstrumentoReactionResponseDTO::new)
                .toList();
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> reactToInstrumento(@Valid @RequestBody InstrumentoReactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = getValidUser(userId);

        Optional<Instrumento> instrumentoOpt = instrumentoRepository.findById(request.getInstrumentoId());
        if (!instrumentoOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Instrumento no encontrado."));
        }

        Instrumento instrumento = instrumentoOpt.get();
        EReaction requestedReaction = resolveReaction(request.getReactionId());

        if (requestedReaction == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Reacción inválida."));
        }

        Reaction reaction = reactionRepository.findByDescription(requestedReaction)
                .orElseGet(() -> reactionRepository.save(new Reaction(requestedReaction)));

        Optional<InstrumentoReaction> existingReaction = instrumentoReactionRepository.findByUserAndInstrumento(user, instrumento);
        if (existingReaction.isPresent()) {
            InstrumentoReaction current = existingReaction.get();
            if (current.getReaction() != null && current.getReaction().getDescription() == requestedReaction) {
                instrumentoReactionRepository.delete(current);
                return ResponseEntity.ok(new MessageResponse("Reacción eliminada"));
            }
            current.setReaction(reaction);
            instrumentoReactionRepository.save(current);
            return ResponseEntity.ok(new MessageResponse("Reacción actualizada"));
        }

        InstrumentoReaction instrumentoReaction = new InstrumentoReaction();
        instrumentoReaction.setUser(user);
        instrumentoReaction.setInstrumento(instrumento);
        instrumentoReaction.setReaction(reaction);
        instrumentoReactionRepository.save(instrumentoReaction);

        return ResponseEntity.ok(new MessageResponse("Reacción registrada"));
    }

    private EReaction resolveReaction(Long reactionId) {
        if (reactionId == null) {
            return null;
        }

        return switch (reactionId.intValue()) {
            case 1 -> EReaction.REACTION_LIKE;
            case 2 -> EReaction.REACTION_LOVE;
            case 3 -> EReaction.REACTION_HATE;
            case 4 -> EReaction.REACTION_SAD;
            case 5 -> EReaction.REACTION_ANGRY;
            default -> null;
        };
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }
}