package pildonitas.pdnt.medication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
