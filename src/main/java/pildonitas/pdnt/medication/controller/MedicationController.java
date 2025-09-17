package pildonitas.pdnt.medication.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pildonitas.pdnt.medication.dto.MedicationRequest;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.services.MedicationService;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MedicationResponse>> getMedicationByUser(@PathVariable Long userId) {
        List<MedicationResponse> medications = medicationService.getMedicationsByUser(userId);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicationResponse> addMedication(@Valid @RequestBody MedicationRequest MedicationRequest, @AuthenticationPrincipal User user) {
        MedicationResponse MedicationResponse = medicationService.addMedication(medicationRequest, user.getId());
        return new ResponseEntity<>(MedicationResponse, HttpStatus.CREATED);
    }

}
