package pildonitas.pdnt.medication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.services.MedicationService;
import pildonitas.pdnt.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

}
