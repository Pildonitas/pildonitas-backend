package pildonitas.pdnt.intake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pildonitas.pdnt.intake.dtos.IntakeRequest;
import pildonitas.pdnt.intake.dtos.IntakeResponse;
import pildonitas.pdnt.intake.status.Status;
import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.medication.MedicationRepository;
import pildonitas.pdnt.security.CustomUserDetail;
import pildonitas.pdnt.user.User;
import pildonitas.pdnt.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IntakeServiceTest {
    @Mock
    private IntakeRepository intakeRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IntakeService intakeService;

    private Medication testMedication;
    private User testUser;
    private CustomUserDetail customUserDetail;
    private Intake testIntake;

    @BeforeEach
    void setUp() {
        testMedication = new Medication();
        testMedication.setId(1L);
        testMedication.setName("Aspirin");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");



        testIntake = new Intake();
        testIntake.setId(1L);
        testIntake.setMedication(testMedication);
        testIntake.setStatus(Status.TAKEN);
        testIntake.setTakenAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should create intake successfully when medication and user exist")
    void addIntake_success() {
        Long medicationId = 1L;
        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(testMedication));
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(intakeRepository.save(any(Intake.class))).thenReturn(testIntake);

        customUserDetail = mock(CustomUserDetail.class);
        when(customUserDetail.getUser()).thenReturn(testUser);

        IntakeResponse result = intakeService.addIntake(medicationId, customUserDetail);

        assertNotNull(result);
        assertEquals(testIntake.getId(), result.id());
        assertEquals(testMedication.getId(), result.medicationId());
        assertEquals(Status.TAKEN.toString(), result.status());

        verify(medicationRepository).findById(medicationId);
        verify(userRepository).findById(testUser.getId());
        verify(intakeRepository).save(any(Intake.class));
    }

    @Test
    @DisplayName("Should return all intakes")
    void getAllIntakes_success() {
        Intake intake2 = Intake.builder()
                .id(2L)
                .medication(testMedication)
                .status(Status.PENDING)
                .build();

        when(intakeRepository.findAll()).thenReturn(List.of(testIntake, intake2));

        List<IntakeResponse> result = intakeService.getAllIntakes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testIntake.getId(), result.get(0).id());
        assertEquals(intake2.getId(), result.get(1).id());

        verify(intakeRepository).findAll();
    }

    @Test
    @DisplayName("Should update intake successfully when intake exists")
    void updateIntake_success() {
        Long intakeId = 1L;
        IntakeRequest request = new IntakeRequest();
        request.setStatus(Status.PENDING);

        when(intakeRepository.findById(intakeId)).thenReturn(Optional.of(testIntake));
        when(intakeRepository.save(testIntake)).thenReturn(testIntake);

        IntakeResponse result = intakeService.updateIntake(intakeId, request);

        assertNotNull(result);
        assertEquals(testIntake.getId(), result.id());
        assertEquals(Status.PENDING, testIntake.getStatus());

        verify(intakeRepository).findById(intakeId);
        verify(intakeRepository).save(testIntake);
    }
}