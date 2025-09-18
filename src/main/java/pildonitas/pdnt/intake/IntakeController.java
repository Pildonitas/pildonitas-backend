package pildonitas.pdnt.intake;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pildonitas.pdnt.intake.dtos.IntakeRequest;
import pildonitas.pdnt.intake.dtos.IntakeResponse;
import pildonitas.pdnt.security.CustomUserDetail;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/intakes")
@RestController
public class IntakeController {

    private final IntakeService intakeService;

    @PostMapping("/{medicationId}" )
    public ResponseEntity<IntakeResponse> addIntake(@PathVariable Long medicationId, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        IntakeResponse intakeResponse = intakeService.addIntake(medicationId, customUserDetail);
        return ResponseEntity.ok(intakeResponse);
    }

    @GetMapping
    public ResponseEntity<List<IntakeResponse>> getAllIntakes(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        List<IntakeResponse> intakes = intakeService.getAllIntakes();
        return new ResponseEntity(intakes, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<IntakeResponse> updateIntake(@PathVariable Long id, @RequestBody IntakeRequest request) {
        IntakeResponse response = intakeService.updateIntake(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}