package pildonitas.pdnt.intake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pildonitas.pdnt.intake.dtos.IntakeRequest;
import pildonitas.pdnt.intake.dtos.IntakeResponse;
import pildonitas.pdnt.intake.status.Status;
import pildonitas.pdnt.security.CustomUserDetail;
import pildonitas.pdnt.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class IntakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IntakeService intakeService;

    private CustomUserDetail customUserDetail;
    private IntakeResponse intakeResponse;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setName("Test User");

        customUserDetail = new CustomUserDetail(testUser);

        intakeResponse = new IntakeResponse(1L, 1L, LocalDateTime.of(2025, 9, 17, 10, 30), Status.TAKEN.toString(), LocalDateTime.of(2025, 9, 17, 10, 33));
    }

    @Test
    @DisplayName("Should create an intake record successfully")
    void addIntake_Success() throws Exception {
        Long medicationId = 1L;
        when(intakeService.addIntake(eq(medicationId), any(CustomUserDetail.class))).thenReturn(intakeResponse);

        mockMvc.perform(post("/api/intakes/{medicationId}", medicationId)
                        .with(user(customUserDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.medicationId").value(1L))
                .andExpect(jsonPath("$.intakeTime").value("2025-09-17T10:30:00"))
                .andExpect(jsonPath("$.status").value("TAKEN"))
                .andExpect(jsonPath("$.takenAt").value("2025-09-17T10:33:00"));
    }

    @Test
    @DisplayName("Should return all intakes successfully")
    void getAllIntakes_Success() throws Exception {
        IntakeResponse intake2 = new IntakeResponse(2L, 2L, LocalDateTime.of(2025, 9, 18, 11, 0), Status.PENDING.toString(), null);
        when(intakeService.getAllIntakes()).thenReturn(List.of(intakeResponse, intake2));

        mockMvc.perform(get("/api/intakes")
                        .with(user(customUserDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value("TAKEN"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].status").value("PENDING"));
    }

    @Test
    @DisplayName("Should update intake successfully")
    void updateIntake_Success() throws Exception {
        Long intakeId = 1L;
        IntakeRequest request = new IntakeRequest();
        request.setStatus(Status.PENDING);
        IntakeResponse updatedIntake = new IntakeResponse(1L, 1L, LocalDateTime.of(2025, 9, 17, 10, 30), Status.TAKEN.toString(), null);

        when(intakeService.updateIntake(eq(intakeId), any(IntakeRequest.class))).thenReturn(updatedIntake);

        mockMvc.perform(put("/api/intakes/{id}", intakeId)
                        .with(user(customUserDetail))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("TAKEN"));
    }
}