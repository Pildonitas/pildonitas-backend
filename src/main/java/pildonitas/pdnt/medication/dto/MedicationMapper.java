package pildonitas.pdnt.medication.dto;

import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.medication.status.Status;
import pildonitas.pdnt.user.User;

public class MedicationMapper {
    public static Medication dtoToEntity(MedicationRequest dto, User user) {
        return Medication.builder()
                .user(user)
                .name(dto.name())
                .description(dto.description())
                .dosage(dto.dosage())
                .allergies(dto.allergies())
                .frequency(dto.frequency())
                .status(dto.status())
                .build();
    }

    public static MedicationResponse entityToDto(Medication medication) {
        return new MedicationResponse(
                medication.getId(),
                medication.getName(),
                medication.getDescription(),
                medication.getDosage(),
                medication.getAllergies(),
                medication.getFrequency(),
                medication.getStatus()
        );
    }
}