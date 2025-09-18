package pildonitas.pdnt.intake;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pildonitas.pdnt.intake.dtos.IntakeMapper;
import pildonitas.pdnt.intake.dtos.IntakeRequest;
import pildonitas.pdnt.intake.dtos.IntakeResponse;
import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.medication.MedicationRepository;
import pildonitas.pdnt.intake.status.Status;
import pildonitas.pdnt.security.CustomUserDetail;
import pildonitas.pdnt.user.User;
import pildonitas.pdnt.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IntakeService {

    private final IntakeRepository intakeRepository;
    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;

    public IntakeResponse addIntake(Long medicationId, CustomUserDetail customUserDetail) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow(EntityNotFoundException::new);
        User userCustomer = userRepository.findById(customUserDetail.getUser().getId()).orElseThrow(EntityNotFoundException::new);

        Intake intake = new Intake();
        intake.setMedication(medication);
        intake.setIntakeTime(LocalDateTime.now());
        intake.setStatus(Status.TAKEN);

        return IntakeMapper.entityToDto(intakeRepository.save(intake));
    }

    public List<IntakeResponse> getAllIntakes() {
        return intakeRepository.findAll()
                .stream()
                .map(IntakeMapper::entityToDto)
                .toList();
    }

    public IntakeResponse updateIntake(Long id, IntakeRequest request) {
        Intake intake = intakeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Intake not found"));

        IntakeMapper.updateEntityFromRequest(intake, request);

        return IntakeMapper.entityToDto(intakeRepository.save(intake));
    }
}