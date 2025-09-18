package pildonitas.pdnt.medication.dto;

import pildonitas.pdnt.medication.status.Status;

import java.time.LocalTime;

public record MedicationResponse(
        Long id,
        String name,
        String description,
        String dosage,
        String allergies,
        String frequency,
        Status status
) {
}
