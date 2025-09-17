package pildonitas.pdnt.medication.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.internal.util.StringHelper;
import pildonitas.pdnt.medication.status.Status;

import java.time.LocalTime;


public record MedicationRequest(
        Long id,

    @NotBlank
    String name,

    @NotBlank
    String description,
    LocalTime hourInitial,

    @NotBlank
    String dosage,
    String allergies,

    @NotBlank
    String frequency,
    LocalTime takenAt,
    Status status
) {
}
