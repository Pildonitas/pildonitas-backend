package pildonitas.pdnt.medication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.medication.MedicationRepository;
import pildonitas.pdnt.medication.dto.MedicationMapper;
import pildonitas.pdnt.medication.dto.MedicationRequest;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.exceptions.custom_exceptions.MedicationNotFoundException;
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
                .map(medication -> MedicationMapper.entityToDto(medication)).toList();
    }

    public MedicationResponse getMedicationById(Long medicationId){
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new MedicationNotFoundException(
                        "Medication with id" + medicationId + "not found"));
        return MedicationMapper.entityToDto(medication);
    }

    public MedicationResponse addMedication(MedicationRequest medicationRequest, Long userId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + "not found"));

        Medication newMedication = MedicationMapper.dtoToEntity(medicationRequest, foundUser);
        Medication savedMedication = medicationRepository.save(newMedication);

        return MedicationMapper.entityToDto(savedMedication);
    }

    public MedicationResponse updateMedication( MedicationRequest medicationRequest, Long userId) {

        List<Medication> medicationsByUsername = medicationRepository.findByUserId(userId);

        Medication existingMedication = medicationsByUsername.stream().filter(medication -> medication.getId().equals(userId)).findFirst().orElseThrow(() -> new MedicationNotFoundException("Medication with id " + userId + " not found"));

        existingMedication.setName(medicationRequest.name());
        existingMedication.setDescription(medicationRequest.description());
        existingMedication.setDosage(medicationRequest.dosage());
        existingMedication.setAllergies(medicationRequest.allergies());
        existingMedication.setFrequency(medicationRequest.frequency());
        existingMedication.setStatus(medicationRequest.status());

        Medication updatedMedication = medicationRepository.save(existingMedication);
        return MedicationMapper.entityToDto(updatedMedication);
    }
        public void deleteMedication(Long medicationId) {
        Medication existingMedication  = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new MedicationNotFoundException("Medication with id " + medicationId + " not found"));
        medicationRepository.delete(existingMedication);
    }

}
