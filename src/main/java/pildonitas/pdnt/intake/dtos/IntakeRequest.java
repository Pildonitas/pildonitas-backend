package pildonitas.pdnt.intake.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pildonitas.pdnt.intake.status.Status;

@Getter
@Setter
public class IntakeRequest {
        @NotNull(message = "Status is required")
        Status status;
}