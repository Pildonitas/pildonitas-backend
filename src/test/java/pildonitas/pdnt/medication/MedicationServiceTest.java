package pildonitas.pdnt.medication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pildonitas.pdnt.medication.dto.MedicationMapper;
import pildonitas.pdnt.medication.dto.MedicationRequest;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.services.MedicationService;
import pildonitas.pdnt.medication.status.Status;
import pildonitas.pdnt.user.User;
import pildonitas.pdnt.user.UserRepository;
import pildonitas.pdnt.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @InjectMocks
    private UserService userService;

    private List<Medication> medications;

    @BeforeEach
    void setUp() {
        Medication medication1 = new Medication();
        medication1.setId(1L);
        medication1.setName("Medication 1");


        Medication medication2 = new Medication();
        medication2.setId(1L);
        medication2.setName("Medication 2");

        this.medications = List.of(medication1, medication2);
    }

    @Test
    @DisplayName("Should return medication for user")
    void shouldReturnMedicationForUser(){
        Long userId = 1L;
        Medication med1 = new Medication();
        med1.setName("Medication 1");
        Medication med2 = new Medication();
        med2.setName("Medication 2");
        List<Medication> medications = List.of(med1, med2);
        given(medicationRepository.findByUserId(userId)).
                willReturn(medications);

        List<MedicationResponse> result = medicationService.getMedicationsByUser(userId);
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Medication 1");
        assertThat(result.get(1).name()).isEqualTo("Medication 2");
        verify(medicationRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Get medication by id when medication exist")
    void getMedicationById_whenMedicationExist_returnsMedicationResponse() {
        Long medicationId = 1L;
        Medication medication = new Medication();
        medication.setId(medicationId);
        medication.setName("Medication 1");
        medication.setDescription("Description test");
        medication.setDosage("50mg");
        medication.setFrequency("3x");

        MedicationResponse expectedResponse = MedicationMapper.entityToDto(medication);
        given(medicationRepository.findById(medicationId)).willReturn(Optional.of(medication));
        MedicationResponse result = medicationService.getMedicationById(medicationId);

        assertThat(result.name()).isEqualTo(expectedResponse.name());
        assertThat(result.description()).isEqualTo(expectedResponse.description());
        assertThat(result.dosage()).isEqualTo(expectedResponse.dosage());
        assertThat(result.frequency()).isEqualTo(expectedResponse.frequency());

        verify(medicationRepository, times(1)).findById(medicationId);
    }
}
