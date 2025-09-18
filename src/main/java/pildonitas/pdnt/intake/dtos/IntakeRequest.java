package pildonitas.pdnt.intake.dtos;

import lombok.Getter;
import lombok.Setter;
import pildonitas.pdnt.intake.status.Status;

@Getter
@Setter
public class IntakeRequest {
        Status status;
}