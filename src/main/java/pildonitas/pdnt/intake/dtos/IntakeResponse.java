package pildonitas.pdnt.intake.dtos;

import java.time.LocalDateTime;

public record IntakeResponse(
        Long id,
        Long medicationId,
        LocalDateTime intakeTime,
        String status,
        LocalDateTime takenAt
) {
}