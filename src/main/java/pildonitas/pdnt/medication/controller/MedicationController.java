package pildonitas.pdnt.medication.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pildonitas.pdnt.medication.dto.MedicationRequest;
import pildonitas.pdnt.medication.dto.MedicationResponse;
import pildonitas.pdnt.medication.services.MedicationService;
import pildonitas.pdnt.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
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

    @PostMapping("/{userId}")
    public ResponseEntity<MedicationResponse> addMedication(@Valid @RequestBody MedicationRequest medicationRequest, @PathVariable Long userId) {
        MedicationResponse medicationResponse = medicationService.addMedication(medicationRequest, medicationRequest.userId());
        return new ResponseEntity<>(medicationResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MedicationResponse> updateMedication(@PathVariable Long userId, @Valid @RequestBody MedicationRequest medicationRequest) {
        MedicationResponse medicationResponse = medicationService.updateMedication(medicationRequest, userId);
        return new ResponseEntity<>(medicationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/del/{medicationId}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long medicationId) {
        medicationService.deleteMedication(medicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}