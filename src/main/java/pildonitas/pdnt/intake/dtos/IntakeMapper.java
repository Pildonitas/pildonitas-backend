package pildonitas.pdnt.intake.dtos;

import pildonitas.pdnt.intake.Intake;

import java.util.List;

public class IntakeMapper {
    public static IntakeResponse entityToDto(Intake intake) {
        return new IntakeResponse(intake.getId(), intake.getMedication().getId(), intake.getIntakeTime(), intake.getStatus().toString(), intake.getTakenAt());
    }

    public static List<IntakeResponse> entityToDto(List<Intake> intakes) {
        return intakes.stream()
                .map(IntakeMapper::entityToDto)
                .toList();
    }

    public static void updateEntityFromRequest(Intake intake, IntakeRequest request) {
        intake.setStatus(request.getStatus());
    }
}