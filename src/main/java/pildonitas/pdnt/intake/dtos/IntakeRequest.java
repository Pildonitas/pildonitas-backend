package pildonitas.pdnt.intake.dtos;

import pildonitas.pdnt.intake.status.Status;

public record IntakeRequest(
        Status status
) {
}