package pildonitas.pdnt.medication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.medication.MedicationRepository;
import pildonitas.pdnt.medication.dto.MedicationMapper;
import pildonitas.pdnt.medication.dto.MedicationRequest;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.exceptions.custom_exceptions.UserNotFoundException;
import pildonitas.pdnt.user.User;
import pildonitas.pdnt.user.UserRepository;

import java.util.List;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, UserRepository userRepository){
    this.medicationRepository = medicationRepository;
    this.userRepository = userRepository;
    }
    public List<MedicationResponse> getMedicationsByUser(Long id) {
        List<Medication> medications = medicationRepository.findByUserId(id);
        return medications.stream()
                .map(medications -> MedicationMapper.entityToDto(medications)).toList();
    }
    public MedicationResponse addMedication(MedicationRequest medicationRequest, Long userId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id" + userId + " not found"));

        Medication newMedication = MedicationMapper.dtoToEntity(medicationRequest, foundUser);
        Medication savedMedication = medicationRepository.save(newMedication);

        return MedicationMapper.entityToDto(savedMedication);
    }
}
